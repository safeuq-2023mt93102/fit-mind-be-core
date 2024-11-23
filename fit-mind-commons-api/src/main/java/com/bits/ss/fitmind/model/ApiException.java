package com.bits.ss.fitmind.model;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ApiException extends Exception {
  private final String type;

  private final String message;
  private final Map<String, Object> metadata;

  public ApiException(String type, String message, Map<String, Object> metadata) {
    this.type = type;
    this.message = message;
    this.metadata = metadata;
  }

  public String getType() {
    return this.type;
  }

  public String getMessage() {
    return this.message;
  }

  public ErrorResponse toErrorResponse() {
    return new ErrorResponse(type, message, metadata);
  }

  public static class ParamNotSet extends ApiException {
    public ParamNotSet(String paramName) {
      super("param_not_set", paramName + " must be set", paramMetadata(paramName));
    }
  }

  public static class ParamNotUnique extends ApiException {
    public ParamNotUnique(String paramName) {
      super("param_not_unique", paramName + " must be unique", paramMetadata(paramName));
    }
  }

  public static class ParamNotEditable extends ApiException {
    public ParamNotEditable(String paramName) {
      super("param_not_editable", paramName + " cannot be edited", paramMetadata(paramName));
    }
  }

  public static class ParamUnexpected extends ApiException {
    public ParamUnexpected(String paramName) {
      super(
          "param_unexpected",
          "Field or value for " + paramName + " is unexpected",
          paramMetadata(paramName));
    }
  }

  public static class ParamPatternInvalid extends ApiException {
    public ParamPatternInvalid(String paramName) {
      super(
          "param_pattern_invalid",
          "Pattern for " + paramName + " is invalid",
          paramMetadata(paramName));
    }
  }

  public static class ParamValueInvalid extends ApiException {
    public ParamValueInvalid(String paramName) {
      super(
          "param_value_invalid",
          "Value for " + paramName + " is invalid",
          paramMetadata(paramName));
    }
  }

  private static Map<String, Object> paramMetadata(String... paramName) {
    return Map.of("param", List.of(paramName));
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (type != null) {
      result.add("\"type\": " + "\"" + type + "\"");
    }
    if (message != null) {
      result.add("\"message\": " + "\"" + message + "\"");
    }
    if (metadata != null) {
      result.add("\"metadata\": " + metadata);
    }
    return result.toString();
  }
}
