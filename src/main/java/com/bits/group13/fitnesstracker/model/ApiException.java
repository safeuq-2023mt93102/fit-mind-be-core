package com.bits.group13.fitnesstracker.model;

public class ApiException extends Exception {
  private final String type;

  private final String message;

  public ApiException(String type, String message) {
    this.type = type;
    this.message = message;
  }

  public String getType() {
    return this.type;
  }

  public String getMessage() {
    return this.message;
  }

  public ErrorResponse toErrorResponse() {
    return new ErrorResponse(type, message);
  }

  public static class ParamNotSet extends ApiException {
    public ParamNotSet(String paramName) {
      super("param_not_set", paramName + " must be set");
    }
  }

  public static class ParamNotUnique extends ApiException {
    public ParamNotUnique(String paramName) {
      super("param_not_unique", paramName + " must be unique");
    }
  }

  public static class ParamNotEditable extends ApiException {
    public ParamNotEditable(String paramName) {
      super("param_not_editable", paramName + " cannot be edited");
    }
  }

  public static class ParamUnexpected extends ApiException {
    public ParamUnexpected(String paramName) {
      super("param_unexpected", "Field or value for " + paramName + " is unexpected");
    }
  }

  public static class ParamPatternInvalid extends ApiException {
    public ParamPatternInvalid(String paramName) {
      super("param_pattern_invalid", "Pattern for " + paramName + " is invalid");
    }
  }

  public static class ParamValueInvalid extends ApiException {
    public ParamValueInvalid(String paramName) {
      super("param_value_invalid", "Value for " + paramName + " is invalid");
    }
  }
}
