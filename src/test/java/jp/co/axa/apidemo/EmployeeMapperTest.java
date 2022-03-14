package jp.co.axa.apidemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;

/** Test for {@link EmployeeMapper} */
class EmployeeMapperTest {

  /** dummy entity dot test */
  private static final Employee ENTITY =
      Employee.builder().department("Sales").name("Peter Kwan").salary(1000).build();

  /** dummy DTO for test */
  private static final EmployeeDTO DTO =
      EmployeeDTO.builder().department("Sales").name("Peter Kwan").salary(1000).build();

  @Test
  void testDTOToEntity() {
    assertEquals(ENTITY, EmployeeMapper.INSTANCE.dtoToEntity(DTO));
  }

  @Test
  void entityToDTO() {
    assertEquals(DTO, EmployeeMapper.INSTANCE.entityToDTO(ENTITY));
  }
}
