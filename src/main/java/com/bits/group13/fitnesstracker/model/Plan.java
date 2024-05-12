package com.bits.group13.fitnesstracker.model;

import com.bits.group13.fitnesstracker.database.PlanRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public final class Plan {

  private final String id;
  private final String planName;

  public Plan(String id, String planName) {
    this.id = id;
    this.planName = planName;
  }

  @JsonProperty
  public String id() {
    return id;
  }

  @JsonProperty
  public String planName() {
    return planName;
  }

  public PlanRecord toPlanRecord() {
    return new PlanRecord(id, planName);
  }

  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Plan) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.planName, that.planName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, planName);
  }

  @Override
  public String toString() {
    return "Plan[" + "id=" + id + ", " + "type=" + planName + ']';
  }
}
