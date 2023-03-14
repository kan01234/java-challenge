package jp.co.axa.apidemo.filters;

import jp.co.axa.apidemo.ApiDemoApplication;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/** unit test of {@link ApiKeyAuthenticationFilter} */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiDemoApplication.class)
@AutoConfigureWebTestClient
public class ApiKeyAuthenticationFilterTest {

  /** Web test client for HTTP request */
  @Autowired private WebTestClient webTestClient;

  /** valid api key read from config */
  @Value("${self.api.key}")
  private String validApiKey;

  private final String invalidApiKey = "some-api-key-invalid";

  @Nested
  class FilterTest {
    @Test
    public void whenAccessRootPathWithoutApiKey_returnNotFound() {
      webTestClient.get().uri("/").exchange().expectStatus().isNotFound();
    }

    @Test
    public void whenAccessRootPathWithApiKey_returnNotFound() {
      webTestClient
          .get()
          .uri("/")
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .exchange()
          .expectStatus()
          .isNotFound();
    }

    @Test
    public void whenProtectedPathWithValidApiKey_returnOk() {
      String uri = "/api/v1/employees";
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody()
          .json("[]");
    }

    @Test
    public void whenProtectedPathWithInvalidApiKey_returnUnauthorized() {
      String uri = "/api/v1/employees";
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, invalidApiKey)
          .exchange()
          .expectStatus()
          .isUnauthorized();
    }

    @Test
    public void whenProtectedPathWithoutApiKey_returnUnauthorized() {
      String uri = "/api/v1/employees";
      webTestClient.get().uri(uri).exchange().expectStatus().isUnauthorized();
    }
  }
}
