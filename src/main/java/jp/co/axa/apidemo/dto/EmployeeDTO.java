package jp.co.axa.apidemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for Employee */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Employee DTO")
public class EmployeeDTO {

  /** Employee id */
  @Schema(description = "Employee Id", hidden = true)
  @Null
  private Long id;

  /** Employee name */
  @Schema(description = "Employee name")
  @NotBlank
  private String name;

  /** Salary of the employee */
  @Schema(description = "Salary of the employee")
  @NotNull
  private Integer salary;

  /** Department of the employee */
  @Schema(description = "Department of the employee")
  @NotBlank
  private String department;
}
