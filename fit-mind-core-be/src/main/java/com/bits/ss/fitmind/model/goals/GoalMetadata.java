package com.bits.ss.fitmind.model.goals;

import com.bits.ss.fitmind.model.EnumSerializable;

public interface GoalMetadata extends EnumSerializable {
  @Override
  GoalType getType();
}
