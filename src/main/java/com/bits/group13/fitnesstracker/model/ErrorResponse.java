package com.bits.group13.fitnesstracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ErrorResponse {
  private final String type;
  private final String message;
  private final Map<String, Object> metadata;

  public ErrorResponse(
      @JsonProperty("type") String type,
      @JsonProperty("message") String message,
      Map<String, Object> metadata) {
    this.type = type;
    this.message = message;
    this.metadata = metadata;
  }

  @JsonProperty("type")
  public String getType() {
    return this.type;
  }

  @JsonProperty("message")
  public String getMessage() {
    return this.message;
  }

  @JsonProperty("metadata")
  public Map<String, Object> getMetadata() {
    return metadata;
  }
}
