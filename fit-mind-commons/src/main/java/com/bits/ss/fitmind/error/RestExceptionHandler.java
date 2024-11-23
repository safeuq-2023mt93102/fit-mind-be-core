package com.bits.ss.fitmind.error;

import com.bits.ss.fitmind.model.ApiException;
import com.bits.ss.fitmind.model.ErrorResponse;
import jakarta.servlet.ServletException;
import java.util.Map;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ResponseEntity<ErrorResponse> handleParseError(
      HttpMessageNotReadableException exception) {
    ApiException apiException = ExceptionUtils.throwableOfType(exception, ApiException.class);
    if (apiException != null) {
      return handleException(apiException);
    }
    return handleGenericException(exception);
  }

  @ExceptionHandler({ApiException.class})
  protected ResponseEntity<ErrorResponse> handleException(ApiException exception) {
    return ResponseEntity.badRequest().body(exception.toErrorResponse());
  }

  @ExceptionHandler({ServletException.class})
  protected ResponseEntity<ErrorResponse> handleException(ServletException exception) {
    return handleGenericException(exception);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
    return ResponseEntity.internalServerError().body(toInternalServerError(exception));
  }

  private ErrorResponse toInternalServerError(Exception exception) {
    LOGGER.error(exception.getMessage(), exception);
    return new ErrorResponse(
        "internal_server_error",
        exception.getMessage(),
        Map.of("stacktrace", exception.getStackTrace()[0]));
  }
}
