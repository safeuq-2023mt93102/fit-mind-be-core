package com.bits.ss.fitmind.model.activity.metadata;

import com.bits.ss.fitmind.model.activity.ActivityMetadata;
import com.bits.ss.fitmind.model.activity.ActivityType;

public class CaloriesActivity implements ActivityMetadata {
  @Override
  public ActivityType getType() {
    return ActivityType.CALORIES_BURNED;
  }
}
