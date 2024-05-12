package com.bits.group13.fitnesstracker.model.activity;

import com.bits.group13.fitnesstracker.model.EnumSerializable;
import com.bits.group13.fitnesstracker.model.activity.metadata.CaloriesActivity;
import com.bits.group13.fitnesstracker.model.activity.metadata.CyclingActivity;
import com.bits.group13.fitnesstracker.model.activity.metadata.HeartRateActivity;
import com.bits.group13.fitnesstracker.model.activity.metadata.SleepActivity;
import com.bits.group13.fitnesstracker.model.activity.metadata.WalkingActivity;

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
