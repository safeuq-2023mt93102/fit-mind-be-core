package com.bits.ss.fitmind.model.activity;

import com.bits.ss.fitmind.model.SourceProvider;
import com.bits.ss.fitmind.model.SourceType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;

public class ActivitySource {
  private final SourceType type;
  private final SourceProvider provider;

  private ActivitySource(SourceType type, SourceProvider provider) {
    this.type = type;
    this.provider = provider;
  }

  @JsonCreator
  public static ActivitySource of(
      @JsonProperty("type") SourceType type, @JsonProperty("provider") SourceProvider provider) {
    return new ActivitySource(type, provider);
  }

  @JsonProperty("type")
  public SourceType getType() {
    return type;
  }

  @JsonProperty("provider")
  public SourceProvider getProvider() {
    return provider;
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (type != null) {
      result.add("\"type\": " + "\"" + type + "\"");
    }
    if (provider != null) {
      result.add("\"provider\": " + "\"" + provider + "\"");
    }
    return result.toString();
  }
}
