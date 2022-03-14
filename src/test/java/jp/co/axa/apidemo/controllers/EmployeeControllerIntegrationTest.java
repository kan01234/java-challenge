package jp.co.axa.apidemo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.filters.ApiKeyAuthenticationFilter;
import jp.co.axa.apidemo.mapper.EmployeeMapper;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/** Integration test for {@link EmployeeController} */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiDemoApplication.class)
@AutoConfigureWebTestClient
class EmployeeControllerIntegrationTest {

  /** Web test client for HTTP request */
  @Autowired private WebTestClient webTestClient;

  /** Repository for Employee */
  @Autowired private EmployeeRepository employeeRepository;

  /** valid api key read from config */
  @Value("${self.api.key}")
  private String validApiKey;

  @AfterEach
  void afterEach() {
    // clean data after each test
    employeeRepository.deleteAll().block();
  }

  @Nested
  class GetAllEmployeeTest {

    @Test
    void whenEmployeesFound_return200WithEmployees() throws JsonProcessingException {
      int size = 5;
      List<EmployeeDTO> createdEmployeesDTO = new ArrayList<>();

      // create employees
      for (int i = 0; i < size; i++) {
        Employee expectedEmployee =
            Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();
        Employee createdEmployee = employeeRepository.save(expectedEmployee).block();
        assertNotNull(createdEmployee);
        createdEmployeesDTO.add(EmployeeMapper.INSTANCE.entityToDTO(createdEmployee));
      }
      assertEquals(size, createdEmployeesDTO.size());

      // when employees is found, should return employees
      String uri = "/api/v1/employees";
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(String.class)
          .isEqualTo(new ObjectMapper().writeValueAsString(createdEmployeesDTO));
    }

    @Test
    void whenEmployeeNotFound_return200WithEmpty() {
      // when employee is not found, should return 404
      String uri = "/api/v1/employees";
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(List.class)
          .isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    void whenExceedOffset_return200WithEmpty() {
      // when employee is not found, should return 404
      String uri = "/api/v1/employees?offset=10000";
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(List.class)
          .isEqualTo(Collections.EMPTY_LIST);
    }
  }

  @Nested
  class GetEmployeeTest {

    @Test
    void whenEmployeeFound_return200WithEmployee() {
      // create employee
      Employee expectedEmployee =
          Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();
      Employee createdEmployee = employeeRepository.save(expectedEmployee).block();
      assertNotNull(createdEmployee);

      // when employee is found, should return employee
      Long id = createdEmployee.getId();
      String uri = "/api/v1/employees/" + id;
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(EmployeeDTO.class)
          .isEqualTo(EmployeeMapper.INSTANCE.entityToDTO(expectedEmployee));
    }

    @Test
    void whenEmployeeNotFound_return404() {
      // when employee is not found, should return 404
      String uri = "/api/v1/employees/1";
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isNotFound();
    }

    @Test
    void whenEmployeeIdNegative_return400() {
      // when employee id is negative, should return 400
      String uri = "/api/v1/employees/-1";
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isBadRequest();
    }
  }

  @Nested
  class DeleteEmployeeTest {

    @Test
    void whenEmployeeExists_return200() {
      // create employee
      Employee expectedEmployee =
          Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();
      Employee createdEmployee = employeeRepository.save(expectedEmployee).block();
      assertNotNull(createdEmployee);

      // when employee is found, should return 200
      Long id = createdEmployee.getId();
      String uri = "/api/v1/employees/" + id;
      webTestClient
          .delete()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isOk();

      // verify delete success, resource not found
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isNotFound();
    }

    @Test
    void whenEmployeeNotFound_return200() {
      // when employee is not found, should return 200
      String uri = "/api/v1/employees/1";
      webTestClient
          .delete()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .exchange()
          .expectStatus()
          .isOk();
    }

    @Test
    void whenEmployeeIdNegative_return400() {
      // when employee id is negative, should return 400
      String uri = "/api/v1/employees/-1";
      webTestClient
          .delete()
          .uri(uri)
          .accept(MediaType.APPLICATION_JSON)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .exchange()
          .expectStatus()
          .isBadRequest();
    }
  }

  @Nested
  class SaveEmployeeTest {

    @Test
    void whenWithoutId_return200() {
      // create employee
      Employee employee =
          Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();

      // when create employee, should return 200
      String uri = "/api/v1/employees";
      WebTestClient.ResponseSpec responseSpec =
          webTestClient
              .post()
              .uri(uri)
              .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
              .contentType(MediaType.APPLICATION_JSON)
              .body(Mono.just(EmployeeMapper.INSTANCE.entityToDTO(employee)), EmployeeDTO.class)
              .exchange()
              .expectStatus()
              .isOk();

      FluxExchangeResult<Employee> fluxExchangeResult = responseSpec.returnResult(Employee.class);
      Employee actualEmployee = fluxExchangeResult.getResponseBody().blockLast();
      assertNotNull(actualEmployee);

      // verify create success, resource found
      Long id = actualEmployee.getId();
      webTestClient
          .get()
          .uri(uri + "/" + id)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(EmployeeDTO.class)
          .isEqualTo(EmployeeMapper.INSTANCE.entityToDTO(actualEmployee));
    }

    @Test
    void whenWithId_return400() {
      // create employee
      Employee expectedEmployee =
          Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();
      Employee createdEmployee = employeeRepository.save(expectedEmployee).block();
      assertNotNull(createdEmployee);

      // when id is exists, id is not allowed, should return 400
      String uri = "/api/v1/employees";
      webTestClient
          .post()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Mono.just(EmployeeMapper.INSTANCE.entityToDTO(expectedEmployee)), EmployeeDTO.class)
          .exchange()
          .expectStatus()
          .isBadRequest();
    }
  }

  @Nested
  class UpdateEmployeeTest {

    @Test
    void whenEmployeeExists_return200() {
      // create employee
      Employee employee =
          Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();
      Employee createdEmployee = employeeRepository.save(employee).block();
      assertNotNull(createdEmployee);

      // when update employee, should return 200
      Long id = createdEmployee.getId();
      String uri = "/api/v1/employees/" + id;
      webTestClient
          .put()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .contentType(MediaType.APPLICATION_JSON)
          .body(
              Mono.just(EmployeeMapper.INSTANCE.entityToDTO(employee.toBuilder().id(null).build())),
              EmployeeDTO.class)
          .exchange()
          .expectStatus()
          .isOk();

      // verify create success, resource found
      webTestClient
          .get()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(EmployeeDTO.class)
          .isEqualTo(EmployeeMapper.INSTANCE.entityToDTO(createdEmployee));
    }

    @Test
    void whenEmployeeNonExists_return200() {
      // create employee
      Employee employee =
          Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();

      // when update employee, should return 200
      String uri = "/api/v1/employees/1";
      webTestClient
          .put()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Mono.just(EmployeeMapper.INSTANCE.entityToDTO(employee)), EmployeeDTO.class)
          .exchange()
          .expectStatus()
          .isNotFound();
    }

    @Test
    void whenEmployeeWithId_return400() {
      // create employee
      Employee employee =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();

      // when id is exists, id is not allowed, should return 400
      String uri = "/api/v1/employees/1";
      webTestClient
          .put()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Mono.just(EmployeeMapper.INSTANCE.entityToDTO(employee)), EmployeeDTO.class)
          .exchange()
          .expectStatus()
          .isBadRequest();
    }

    @Test
    void whenEmployeeIdNegative_return400() {
      // create employee
      Employee employee =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();

      // when id is negative, should return 400
      String uri = "/api/v1/employees/1";
      webTestClient
          .put()
          .uri(uri)
          .header(ApiKeyAuthenticationFilter.X_API_KEY_HEADER, validApiKey)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Mono.just(EmployeeMapper.INSTANCE.entityToDTO(employee)), EmployeeDTO.class)
          .exchange()
          .expectStatus()
          .isBadRequest();
    }
  }
}
