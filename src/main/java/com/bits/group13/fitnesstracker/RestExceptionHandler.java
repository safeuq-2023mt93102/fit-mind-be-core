package com.bits.group13.fitnesstracker;

import com.bits.group13.fitnesstracker.model.ApiException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ResponseEntity<Object> handleParseError(HttpMessageNotReadableException exception) {
    ApiException apiException = ExceptionUtils.throwableOfType(exception, ApiException.class);
    if (apiException != null) {
      return handleApiException(apiException);
    }
    throw exception;
  }

  @ExceptionHandler(ApiException.class)
  protected ResponseEntity<Object> handleApiException(ApiException exception) {
    return ResponseEntity.badRequest().body(exception);
  }
}
