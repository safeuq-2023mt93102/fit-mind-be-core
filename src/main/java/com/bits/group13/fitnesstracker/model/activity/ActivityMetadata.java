package com.bits.group13.fitnesstracker.model.activity;

import com.bits.group13.fitnesstracker.model.EnumSerializable;

public interface ActivityMetadata extends EnumSerializable {
  @Override
  ActivityType getActivityType();
}
