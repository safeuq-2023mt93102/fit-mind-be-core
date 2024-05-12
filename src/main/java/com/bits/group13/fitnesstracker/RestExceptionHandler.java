package com.bits.group13.fitnesstracker;

import com.bits.group13.fitnesstracker.model.ApiException;
import com.bits.group13.fitnesstracker.model.ErrorResponse;
import jakarta.servlet.ServletException;
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

  @ExceptionHandler({ApiException.class, ServletException.class})
  protected ResponseEntity<ErrorResponse> handleException(ApiException exception) {
    return ResponseEntity.badRequest().body(exception.toErrorResponse());
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
    return ResponseEntity.internalServerError().body(toInternalServerError(exception));
  }

  private ErrorResponse toInternalServerError(Exception exception) {
    LOGGER.error(exception.getMessage(), exception);
    return new ErrorResponse("internal_server_error", exception.getMessage());
  }
}
