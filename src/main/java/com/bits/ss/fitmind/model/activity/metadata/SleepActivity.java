package com.bits.ss.fitmind.model.activity.metadata;

import com.bits.ss.fitmind.model.activity.ActivityMetadata;
import com.bits.ss.fitmind.model.activity.ActivityType;

public class SleepActivity implements ActivityMetadata {
  @Override
  public ActivityType getType() {
    return ActivityType.SLEEP;
  }
}
