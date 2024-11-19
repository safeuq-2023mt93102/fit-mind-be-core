package com.bits.group13.fitnesstracker.model.goals;

import com.bits.group13.fitnesstracker.model.EnumSerializable;

public interface GoalMetadata extends EnumSerializable {
  @Override
  GoalType getActivityType();
}
