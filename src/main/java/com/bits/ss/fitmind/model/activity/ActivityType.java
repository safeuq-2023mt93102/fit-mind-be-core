package com.bits.ss.fitmind.model.activity;

import com.bits.ss.fitmind.model.EnumSerializable;
import com.bits.ss.fitmind.model.activity.metadata.CaloriesActivity;
import com.bits.ss.fitmind.model.activity.metadata.CyclingActivity;
import com.bits.ss.fitmind.model.activity.metadata.HeartRateActivity;
import com.bits.ss.fitmind.model.activity.metadata.SleepActivity;
import com.bits.ss.fitmind.model.activity.metadata.WalkingActivity;

public enum ActivityType implements EnumSerializable.Type<ActivityType> {
  WALKING(WalkingActivity.class),
  CYCLING(CyclingActivity.class),
  CALORIES_BURNED(CaloriesActivity.class),
  SLEEP(SleepActivity.class),
  HEART_RATE(HeartRateActivity.class);

  private final Class<? extends ActivityMetadata> typeClass;

  ActivityType(Class<? extends ActivityMetadata> typeClass) {
    this.typeClass = typeClass;
  }

  @Override
  public Class<? extends ActivityMetadata> getTypeClass() {
    return typeClass;
  }
}
