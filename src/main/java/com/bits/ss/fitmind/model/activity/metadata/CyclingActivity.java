package com.bits.ss.fitmind.model.activity.metadata;

import com.bits.ss.fitmind.model.activity.ActivityMetadata;
import com.bits.ss.fitmind.model.activity.ActivityType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CyclingActivity implements ActivityMetadata {
  private final double distance;
  private final DistanceUnit unit;

  private CyclingActivity(double distance, DistanceUnit unit) {
    this.distance = distance;
    this.unit = unit;
  }

  @JsonCreator
  public static CyclingActivity of(
      @JsonProperty("distance") double distance, @JsonProperty("unit") DistanceUnit unit) {
    return new CyclingActivity(distance, unit);
  }

  @JsonProperty("distance")
  public double getDistance() {
    return distance;
  }

  @JsonProperty("unit")
  public DistanceUnit getUnit() {
    return unit;
  }

  @Override
  public ActivityType getType() {
    return ActivityType.CYCLING;
  }
}
