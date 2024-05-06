package com.bits.group13.fitnesstracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = {"localizedMessage", "cause", "stackTrace", "suppressed"})
public class ApiException extends Exception {
  private final String type;

  private final String message;

  public ApiException(@JsonProperty("type") String type, @JsonProperty("message") String message) {
    this.type = type;
    this.message = message;
  }

  @JsonProperty("type")
  public String getType() {
    return this.type;
  }

  @JsonProperty("message")
  public String getMessage() {
    return this.message;
  }

  public static class ParamEmpty extends ApiException {
    public ParamEmpty(String paramName) {
      super("param_empty", paramName + " cannot be empty");
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

  public static class ParamPatternInvalid extends ApiException {
    public ParamPatternInvalid(String paramName) {
      super("param_pattern_invalid", "Pattern for " + paramName + " is invalid");
    }
  }
}
