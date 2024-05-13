package com.bits.group13.fitnesstracker.model.goals;

import com.bits.group13.fitnesstracker.model.EnumSerializable;

public enum GoalType implements EnumSerializable.Type<GoalType> {
  EXERCISE(ExerciseGoal.class);

  private final Class<? extends ExerciseGoal> typeClass;

  GoalType(Class<? extends ExerciseGoal> typeClass) {
    this.typeClass = typeClass;
  }

  @Override
  public Class<? extends ExerciseGoal> getTypeClass() {
    return typeClass;
  }
}
