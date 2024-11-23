package com.bits.ss.fitmind.model.activity.metadata;

import com.bits.ss.fitmind.model.activity.ActivityMetadata;
import com.bits.ss.fitmind.model.activity.ActivityType;

public class HeartRateActivity implements ActivityMetadata {
  @Override
  public ActivityType getType() {
    return ActivityType.HEART_RATE;
  }
}
