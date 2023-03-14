package jp.co.axa.apidemo.handler;

import java.util.NoSuchElementException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Controller advice for exception handler */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

  /**
   * to handle NoSuchElementException and response 404
   *
   * @return ResponseEntity with 404 status
   */
  @ExceptionHandler(value = NoSuchElementException.class)
  protected ResponseEntity<Void> handleNoSuchElementException() {
    return ResponseEntity.notFound().build();
  }

  /**
   * to handle ConstraintViolationException and response 400
   *
   * @return ResponseEntity with 404 status
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  protected ResponseEntity<Void> handleConstraintViolationException() {
    return ResponseEntity.badRequest().build();
  }
}
