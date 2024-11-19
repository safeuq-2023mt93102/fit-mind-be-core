package com.bits.group13.fitnesstracker.model.activity.metadata;

import com.bits.group13.fitnesstracker.model.activity.ActivityMetadata;
import com.bits.group13.fitnesstracker.model.activity.ActivityType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;

public class WalkingActivity implements ActivityMetadata {
  private final long steps;

  private WalkingActivity(long steps) {
    this.steps = steps;
  }

  @JsonCreator
  public static WalkingActivity of(@JsonProperty("steps") long steps) {
    return new WalkingActivity(steps);
  }

  @JsonProperty("steps")
  public long getSteps() {
    return steps;
  }

  @Override
  public ActivityType getType() {
    return ActivityType.WALKING;
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    result.add("\"steps\": " + steps);
    return result.toString();
  }
}
