package jp.co.axa.apidemo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApiDemoApplicationTests {

  /** configuration to be tested */
  private ApiDemoApplication configuration;

  @BeforeEach
  void beforeEach() {
    configuration = new ApiDemoApplication();
  }

  @Test
  void testInitializer() {
    assertDoesNotThrow(() -> configuration.initializer(mock(ConnectionFactory.class)));
  }
}
