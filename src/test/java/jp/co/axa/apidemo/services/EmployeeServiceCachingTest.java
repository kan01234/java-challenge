package jp.co.axa.apidemo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.mapper.EmployeeMapper;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

/** class to test caching of EmployeeService */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiDemoApplication.class)
class EmployeeServiceCachingTest {

  /** Auto closeable for open mocks */
  private AutoCloseable autoCloseable;

  /** mock employee repository */
  @Mock private EmployeeRepository employeeRepository;

  /** mock cache */
  @Autowired private CacheManager cacheManager;

  /** employee service to be tested */
  private EmployeeServiceImpl employeeService;

  /** before each */
  @BeforeEach
  void beforeEach() {
    autoCloseable = openMocks(this);
    employeeService = new EmployeeServiceImpl(cacheManager, employeeRepository);
  }

  /**
   * after each
   *
   * @throws Exception auto closeable exception
   */
  @AfterEach
  void afterEach() throws Exception {
    autoCloseable.close();
  }

  @Nested
  class GetEmployeeTest {
    @Test
    void givenRedisCaching_whenEmployeeFound_returnEmployeeFromCache() {
      final long employeeId = 1L;
      Employee entity =
          Employee.builder()
              .id(employeeId)
              .department("Sales")
              .name("Peter Kwan")
              .salary(1000)
              .build();
      when(employeeRepository.findById(1L)).thenReturn(Mono.just(entity));

      EmployeeDTO employeeCacheMiss = employeeService.getEmployee(employeeId).block();
      EmployeeDTO employeeCacheHit = employeeService.getEmployee(employeeId).block();

      assertEquals(employeeCacheHit, employeeCacheMiss);
      assertEquals(EmployeeMapper.INSTANCE.entityToDTO(entity), employeeCacheHit);
      verify(employeeRepository, times(1)).findById(employeeId);
    }
  }
}
