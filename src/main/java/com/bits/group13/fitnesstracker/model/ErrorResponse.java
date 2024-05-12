package com.bits.group13.fitnesstracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {
  private final String type;
  private final String message;

  public ErrorResponse(@JsonProperty("type") String type, @JsonProperty("message") String message) {
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
}
