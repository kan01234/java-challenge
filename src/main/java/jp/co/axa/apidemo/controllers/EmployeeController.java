package jp.co.axa.apidemo.controllers;

import static jp.co.axa.apidemo.filters.ApiKeyAuthenticationFilter.X_API_KEY_HEADER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import jp.co.axa.apidemo.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** Controller for Employee CRUD */
@RestController
@RequestMapping("/api/v1")
@Slf4j
@Validated
public class EmployeeController {

  /** Employee id constant */
  private static final String EMPLOYEE_ID = "employeeId";

  /** Employee id constant */
  private static final String EMPLOYEE_PATH = "/employees";

  /** Employee id constant */
  private static final String EMPLOYEE_WITH_ID_PATH = EMPLOYEE_PATH + "/{employeeId}";

  /** Service for Employee */
  @Autowired private EmployeeService employeeService;

  /**
   * Get employees
   *
   * @param page page from request param
   * @param pageSize pageSize from request param
   * @return list of found employees
   */
  @Operation(
      summary = "Get employees",
      responses = {
        @ApiResponse(description = "Get list of found employees", responseCode = "200"),
        @ApiResponse(description = "Validation error", responseCode = "400")
      },
      security = @SecurityRequirement(name = X_API_KEY_HEADER),
      parameters =
          @Parameter(
              name = X_API_KEY_HEADER,
              in = ParameterIn.HEADER,
              required = true,
              description = "Demo purpose, use \"some-api-key\""))
  @GetMapping(EMPLOYEE_PATH)
  public Flux<EmployeeDTO> getEmployees(
      @Valid @Min(0) @RequestParam(defaultValue = "0") int page,
      @Valid @Max(1000) @RequestParam(defaultValue = "100") int pageSize) {
    return employeeService.retrieveEmployees(page, pageSize);
  }

  /**
   * Get employee
   *
   * @param employeeId the employee id to be got
   * @return found employee
   */
  @Operation(
      summary = "Get employee",
      responses = {
        @ApiResponse(description = "Get found employee by employee id", responseCode = "200"),
        @ApiResponse(description = "Validation error", responseCode = "400"),
        @ApiResponse(description = "Employee for the specific id not found", responseCode = "404")
      },
      security = @SecurityRequirement(name = X_API_KEY_HEADER),
      parameters =
          @Parameter(
              name = X_API_KEY_HEADER,
              in = ParameterIn.HEADER,
              required = true,
              description = "Demo purpose, use \"some-api-key\""))
  @GetMapping(EMPLOYEE_WITH_ID_PATH)
  public Mono<EmployeeDTO> getEmployee(
      @Valid @Min(0) @PathVariable(name = EMPLOYEE_ID) Long employeeId) {
    return employeeService.getEmployee(employeeId);
  }

  /**
   * Save employee
   *
   * @param mono the employee mono to be saved
   */
  @Operation(
      summary = "Save employee",
      responses = {
        @ApiResponse(description = "Save employee", responseCode = "200"),
        @ApiResponse(description = "Validation error", responseCode = "400"),
      },
      security = @SecurityRequirement(name = X_API_KEY_HEADER),
      parameters =
          @Parameter(
              name = X_API_KEY_HEADER,
              in = ParameterIn.HEADER,
              required = true,
              description = "Demo purpose, use \"some-api-key\""))
  @PostMapping(EMPLOYEE_PATH)
  public Mono<EmployeeDTO> saveEmployee(@Valid @RequestBody Mono<EmployeeDTO> mono) {
    return mono.flatMap(employeeService::saveEmployee);
  }

  /**
   * Delete employee
   *
   * @param employeeId the employee id to be deleted
   */
  @Operation(
      summary = "Delete employee",
      responses = {
        @ApiResponse(description = "Delete employee by employee id", responseCode = "200"),
        @ApiResponse(description = "Validation error", responseCode = "400"),
        @ApiResponse(description = "Employee for the specific id not found", responseCode = "404")
      },
      security = @SecurityRequirement(name = X_API_KEY_HEADER),
      parameters =
          @Parameter(
              name = X_API_KEY_HEADER,
              in = ParameterIn.HEADER,
              required = true,
              description = "Demo purpose, use \"some-api-key\""))
  @DeleteMapping(EMPLOYEE_WITH_ID_PATH)
  public Mono<Void> deleteEmployee(
      @Valid @Min(0) @PathVariable(name = EMPLOYEE_ID) Long employeeId) {
    return employeeService.deleteEmployee(employeeId);
  }

  /**
   * Update employee
   *
   * @param mono the employee mono to be updated
   * @param employeeId the employee id to be updated
   */
  @Operation(
      summary = "Update employee",
      responses = {
        @ApiResponse(description = "Update employee with employee id", responseCode = "200"),
        @ApiResponse(description = "Validation error", responseCode = "400"),
        @ApiResponse(description = "Employee for the specific id not found", responseCode = "404")
      },
      security = @SecurityRequirement(name = X_API_KEY_HEADER),
      parameters =
          @Parameter(
              name = X_API_KEY_HEADER,
              in = ParameterIn.HEADER,
              required = true,
              description = "Demo purpose, use \"some-api-key\""))
  @PutMapping(EMPLOYEE_WITH_ID_PATH)
  public Mono<EmployeeDTO> updateEmployee(
      @Valid @RequestBody Mono<EmployeeDTO> mono,
      @Valid @Min(0) @PathVariable(name = EMPLOYEE_ID) Long employeeId) {
    return mono.flatMap(
        employee -> {
          employee.setId(employeeId);
          return employeeService.updateEmployee(employee);
        });
  }
}
