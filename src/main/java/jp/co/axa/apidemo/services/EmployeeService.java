package jp.co.axa.apidemo.services;

import java.util.NoSuchElementException;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service for Employee */
public interface EmployeeService {

  /**
   * Retrieve list of employees
   *
   * @return list of employees
   */
  Flux<EmployeeDTO> retrieveEmployees(int offset, int limit);

  /**
   * To get specify employee by employeeId cache with employee id
   *
   * @param employeeId employeeId
   * @return employee if found
   * @throws NoSuchElementException when not found
   */
  Mono<EmployeeDTO> getEmployee(Long employeeId) throws NoSuchElementException;

  /**
   * Save employee
   *
   * @param employee the employee to be saved
   */
  Mono<EmployeeDTO> saveEmployee(EmployeeDTO employee);

  /**
   * Delete employee cache evict by employeeId
   *
   * @param employeeId the employee id to be deleted
   */
  Mono<Void> deleteEmployee(Long employeeId);

  /**
   * Update employee cache put by employeeId
   *
   * @param employee the employee to be updated
   */
  Mono<EmployeeDTO> updateEmployee(EmployeeDTO employee);
}
