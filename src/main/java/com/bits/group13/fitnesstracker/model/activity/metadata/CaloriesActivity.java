package com.bits.group13.fitnesstracker.model.activity.metadata;

import com.bits.group13.fitnesstracker.model.activity.ActivityMetadata;
import com.bits.group13.fitnesstracker.model.activity.ActivityType;

public class CaloriesActivity implements ActivityMetadata {
  @Override
  public ActivityType getType() {
    return ActivityType.CALORIES_BURNED;
  }
}
