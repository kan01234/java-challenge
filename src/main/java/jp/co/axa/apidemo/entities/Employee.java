package jp.co.axa.apidemo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/** Entity for Employee */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

  /** Employee id */
  @Id private Long id;

  /** Employee name */
  private String name;

  /** Salary of the employee */
  private Integer salary;

  /** Department of the employee */
  private String department;
}
