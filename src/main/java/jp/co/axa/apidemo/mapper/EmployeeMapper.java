package jp.co.axa.apidemo.mapper;

import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/** Mapper for Employee */
@Mapper
public interface EmployeeMapper {

  /** Employee mapper instance */
  EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

  /**
   * Employee DTO to entity
   *
   * @param dto the DTO
   * @return the entity
   */
  Employee dtoToEntity(EmployeeDTO dto);

  /**
   * Employee entity to DTO
   *
   * @param entity the entity
   * @return the DTO
   */
  @InheritInverseConfiguration
  EmployeeDTO entityToDTO(Employee entity);
}
