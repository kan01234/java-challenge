package jp.co.axa.apidemo.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/** Test for {@link ControllerExceptionHandler} */
class ControllerExceptionHandlerTest {

  /** handler to be tested */
  private ControllerExceptionHandler handler;

  @BeforeEach
  void beforeEach() {
    handler = new ControllerExceptionHandler();
  }

  @Test
  void testHandleNoSuchElementException() {
    assertEquals(ResponseEntity.notFound().build(), handler.handleNoSuchElementException());
  }
}
