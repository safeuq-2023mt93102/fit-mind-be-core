package com.bits.group13.fitnesstracker.model.plan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class PlanRequest {
  private final PlanLevel level;
  private final PlanTarget target;

  private PlanRequest(PlanLevel level, PlanTarget target) {
    this.level = level;
    this.target = target;
  }

  @JsonCreator
  public static PlanRequest of(
      @JsonProperty("level") PlanLevel level, @JsonProperty("target") PlanTarget target) {
    return new PlanRequest(level, target);
  }

  @JsonProperty("level")
  public PlanLevel getLevel() {
    return level;
  }

  @JsonProperty("target")
  public PlanTarget getTarget() {
    return target;
  }
}
