package com.bits.group13.fitnesstracker.model;

import com.bits.group13.fitnesstracker.database.GoalRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public final class Goal {

  private final String id;
  private final String type;

  public Goal(String id, String type) {
    this.id = id;
    this.type = type;
  }

  @JsonProperty
  public String id() {
    return id;
  }

  @JsonProperty
  public String type() {
    return type;
  }

  public GoalRecord toGoalRecord() {
    return new GoalRecord(id, type);
  }

  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Goal) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type);
  }

  @Override
  public String toString() {
    return "Goal[" + "id=" + id + ", " + "type=" + type + ']';
  }
}
