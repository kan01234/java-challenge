package jp.co.axa.apidemo.services;

import java.util.NoSuchElementException;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.mapper.EmployeeMapper;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Service Impl for Employee */
@Service
public class EmployeeServiceImpl implements EmployeeService {

  /** Repository for Employee */
  private EmployeeRepository employeeRepository;

  /** cache for employee */
  private final Cache cache;

  /**
   * Constructor
   *
   * @param cacheManager the cache manager
   * @param employeeRepository the repository for employee
   */
  @Autowired
  public EmployeeServiceImpl(CacheManager cacheManager, EmployeeRepository employeeRepository) {
    this.cache = cacheManager.getCache("employee");
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Flux<EmployeeDTO> retrieveEmployees(int page, int pageSize) {
    return employeeRepository
        .findAllBy(PageRequest.of(page, pageSize))
        // map back to DTO
        .map(EmployeeMapper.INSTANCE::entityToDTO);
  }

  @Override
  public Mono<EmployeeDTO> getEmployee(Long employeeId) throws NoSuchElementException {
    // get from cache or load from database
    return cache
        .get(
            employeeId,
            () ->
                employeeRepository
                    .findById(employeeId)
                    // map back to DTO
                    .map(EmployeeMapper.INSTANCE::entityToDTO)
                    .cache())
        // not found employee
        .switchIfEmpty(
            Mono.error(new NoSuchElementException("employeeId: " + employeeId + " not found")));
  }

  @Override
  public Mono<EmployeeDTO> saveEmployee(EmployeeDTO dto) {
    // save employee
    return employeeRepository
        .save(EmployeeMapper.INSTANCE.dtoToEntity(dto))
        // map back to DTO
        .map(EmployeeMapper.INSTANCE::entityToDTO)
        // put to cache
        .doOnSuccess(savedDTO -> cache.put(savedDTO.getId(), Mono.just(savedDTO).cache()));
  }

  @Override
  public Mono<Void> deleteEmployee(Long employeeId) {
    // delete employee bby id
    return employeeRepository
        .deleteById(employeeId)
        // evict from cache
        .doOnSuccess(savedDTO -> cache.evict(employeeId));
  }

  @Override
  public Mono<EmployeeDTO> updateEmployee(EmployeeDTO dto) {
    return employeeRepository
        .save(EmployeeMapper.INSTANCE.dtoToEntity(dto))
        // map back to DTO
        .map(EmployeeMapper.INSTANCE::entityToDTO)
        // put to cache
        .doOnSuccess(savedDTO -> cache.put(savedDTO.getId(), Mono.just(savedDTO).cache()))
        // update not exists resource, throw no such element exception
        .onErrorMap(
            TransientDataAccessResourceException.class,
            e -> new NoSuchElementException("employeeId: " + dto.getId() + " not found"));
  }
}
