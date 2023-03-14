package jp.co.axa.apidemo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Test for {@link EmployeeServiceImpl} */
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

  /** Auto closeable for open mocks */
  private AutoCloseable autoCloseable;

  /** mock employee repository */
  @Mock private EmployeeRepository employeeRepository;

  /** mock cache manager */
  @Mock private CacheManager cacheManager;

  /** mock cache */
  @Mock private Cache cache;

  /** employee service to be tested */
  private EmployeeServiceImpl employeeService;

  /** before each */
  @BeforeEach
  void beforeEach() {
    autoCloseable = openMocks(this);
    when(cacheManager.getCache("employee")).thenReturn(cache);
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
  class RetrieveEmployeesTest {

    @Test
    void whenEmployeesFound_returnEmployees() {
      // mock find by id
      Employee entity1 =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();
      Employee entity2 =
          Employee.builder().id(2L).department("IT").name("Peter Kwan").salary(2000).build();
      Employee entity3 =
          Employee.builder().id(3L).department("Marketing").name("Peter Kwan").salary(3000).build();
      when(employeeRepository.findAllBy(PageRequest.of(0, 10)))
          .thenReturn(Flux.just(entity1, entity2, entity3));

      // when employee is found, should return employee
      Flux<EmployeeDTO> flux = employeeService.retrieveEmployees(0, 10);
      Mono<List<EmployeeDTO>> mono = flux.collectList();
      List<EmployeeDTO> actualEmployee = assertDoesNotThrow(() -> mono.block());
      List<EmployeeDTO> expectedEmployees = new ArrayList<>();
      expectedEmployees.add(EmployeeMapper.INSTANCE.entityToDTO(entity1));
      expectedEmployees.add(EmployeeMapper.INSTANCE.entityToDTO(entity2));
      expectedEmployees.add(EmployeeMapper.INSTANCE.entityToDTO(entity3));
      assertEquals(expectedEmployees, actualEmployee);
    }

    @Test
    void whenEmployeeNotFound_returnEmpty() {
      // mock find by id return empty
      when(employeeRepository.findAllBy(PageRequest.of(0, 10))).thenReturn(Flux.empty());

      // when employee is not found, should throw NoSuchElementException
      Flux<EmployeeDTO> flux = employeeService.retrieveEmployees(0, 10);
      Mono<List<EmployeeDTO>> mono = flux.collectList();
      List<EmployeeDTO> actualEmployee = assertDoesNotThrow(() -> mono.block());
      assertTrue(actualEmployee.isEmpty());
    }

    @Test
    void whenException_throwException() {
      // mock find by id throw exception
      IllegalArgumentException expectedException = new IllegalArgumentException("error");
      when(employeeRepository.findAllBy(PageRequest.of(0, 10)))
          .thenReturn(Flux.error(expectedException));

      // throw IllegalArgumentException
      Flux<EmployeeDTO> flux = employeeService.retrieveEmployees(0, 10);
      IllegalArgumentException actualException =
          assertThrows(IllegalArgumentException.class, flux::blockLast);
      assertEquals(expectedException, actualException);
    }
  }

  @Nested
  class GetEmployeeTest {

    @Test
    void whenEmployeeFound_returnEmployee() {
      // mock find by id
      EmployeeDTO dto =
          EmployeeDTO.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();
      when(cache.get(eq(1L), any(Callable.class))).thenReturn(Mono.just(dto));

      // when employee is found, should return employee
      Mono<EmployeeDTO> mono = employeeService.getEmployee(1L);
      EmployeeDTO actualEmployee = assertDoesNotThrow(() -> mono.block());
      assertEquals(dto, actualEmployee);
    }

    @Test
    void whenEmployeeNotFound_throwNoSuchElementException() {
      // mock find by id return empty
      when(cache.get(eq(1L), any(Callable.class))).thenReturn(Mono.empty());

      // when employee is not found, should throw NoSuchElementException
      Mono<EmployeeDTO> mono = employeeService.getEmployee(1L);
      assertThrows(NoSuchElementException.class, mono::block);
    }

    @Test
    void whenException_throwException() {
      // mock find by id throw exception
      IllegalArgumentException expectedException = new IllegalArgumentException("error");
      when(cache.get(eq(1L), any(Callable.class))).thenReturn(Mono.error(expectedException));

      // throw IllegalArgumentException
      Mono<EmployeeDTO> mono = employeeService.getEmployee(1L);
      IllegalArgumentException actualException =
          assertThrows(IllegalArgumentException.class, mono::block);
      assertEquals(expectedException, actualException);
    }
  }

  @Nested
  class SaveEmployeeTest {

    @Test
    void whenSaveSuccess_returnEmployee() {
      // mock save
      Employee entity =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();
      when(employeeRepository.save(entity)).thenReturn(Mono.just(entity));

      // when save employee, should return employee
      EmployeeDTO expectedEmployee = EmployeeMapper.INSTANCE.entityToDTO(entity);
      Mono<EmployeeDTO> mono = employeeService.saveEmployee(expectedEmployee);
      EmployeeDTO actualEmployee = assertDoesNotThrow(() -> mono.block());
      assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    void whenException_throwException() {
      // mock save throw exception
      Employee entity =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();
      IllegalArgumentException expectedException = new IllegalArgumentException("error");
      when(employeeRepository.save(entity)).thenReturn(Mono.error(expectedException));

      // throw IllegalArgumentException
      Mono<EmployeeDTO> mono =
          employeeService.saveEmployee(EmployeeMapper.INSTANCE.entityToDTO(entity));
      IllegalArgumentException actualException =
          assertThrows(IllegalArgumentException.class, mono::block);
      assertEquals(expectedException, actualException);
    }
  }

  @Nested
  class UpdateEmployeeTest {

    @Test
    void whenSaveSuccess_returnEmployee() {
      // mock save
      Employee entity =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();
      when(employeeRepository.save(entity)).thenReturn(Mono.just(entity));

      // when update empty, should return employee
      EmployeeDTO expectedEmployee = EmployeeMapper.INSTANCE.entityToDTO(entity);
      Mono<EmployeeDTO> mono = employeeService.updateEmployee(expectedEmployee);
      EmployeeDTO actualEmployee = assertDoesNotThrow(() -> mono.block());
      assertEquals(expectedEmployee, actualEmployee);
      verify(cache, times(1)).put(eq(1L), any());
    }

    @Test
    void whenException_throwException() {
      // mock save throw exception
      Employee entity =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();
      IllegalArgumentException expectedException = new IllegalArgumentException("error");
      when(employeeRepository.save(entity)).thenReturn(Mono.error(expectedException));

      // throw IllegalArgumentException
      Mono<EmployeeDTO> mono =
          employeeService.updateEmployee(EmployeeMapper.INSTANCE.entityToDTO(entity));
      IllegalArgumentException actualException =
          assertThrows(IllegalArgumentException.class, mono::block);
      assertEquals(expectedException, actualException);
      verify(cache, never()).put(anyLong(), any());
    }

    @Test
    void whenTransientDataAccessResourceException_throwNoSuchElementException() {
      // mock save throw TransientDataAccessResourceException
      Employee entity =
          Employee.builder().id(1L).department("Sales").name("Peter Kwan").salary(1000).build();
      TransientDataAccessResourceException expectedException =
          new TransientDataAccessResourceException("error");
      when(employeeRepository.save(entity)).thenReturn(Mono.error(expectedException));

      // throw NoSuchElementException
      Mono<EmployeeDTO> mono =
          employeeService.updateEmployee(EmployeeMapper.INSTANCE.entityToDTO(entity));
      assertThrows(NoSuchElementException.class, mono::block);
      verify(cache, never()).put(anyLong(), any());
    }
  }

  @Nested
  class DeleteEmployeeTest {

    @Test
    void whenDeleteSuccess() {
      // mock delete
      when(employeeRepository.deleteById(1L)).thenReturn(Mono.empty());

      // when delete employee, do nothing
      Mono<Void> mono = employeeService.deleteEmployee(1L);
      assertDoesNotThrow(() -> mono.block());
      verify(cache, times(1)).evict(1L);
    }

    @Test
    void whenException_throwException() {
      // mock delete throw exception
      IllegalArgumentException expectedException = new IllegalArgumentException("error");
      when(employeeRepository.deleteById(1L)).thenReturn(Mono.error(expectedException));

      // throw IllegalArgumentException
      Mono<Void> mono = employeeService.deleteEmployee(1L);
      IllegalArgumentException actualException =
          assertThrows(IllegalArgumentException.class, mono::block);
      assertEquals(expectedException, actualException);
      verify(cache, never()).evict(anyLong());
    }
  }
}
