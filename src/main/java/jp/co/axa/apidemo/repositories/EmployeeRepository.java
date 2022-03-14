package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/** Repository for Employee */
@Repository
public interface EmployeeRepository extends R2dbcRepository<Employee, Long> {

  /**
   * Find all by pageable
   *
   * @param pageable the pageable
   * @return flux of employees
   */
  Flux<Employee> findAllBy(Pageable pageable);
}
