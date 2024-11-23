package com.bits.ss.fitmind.model.activity;

import com.bits.ss.fitmind.model.EnumSerializable;

public interface ActivityMetadata extends EnumSerializable {
  @Override
  ActivityType getType();
}
