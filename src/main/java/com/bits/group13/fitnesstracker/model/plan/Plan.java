package com.bits.group13.fitnesstracker.model.plan;

import com.bits.group13.fitnesstracker.database.PlanRecord;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Period;
import java.util.List;
import java.util.StringJoiner;

public final class Plan {
  private final String id;
  private final String planName;
  private final Period duration;
  private final List<PlanWorkout> workouts;

  private Plan(String id, String planName, Period duration, List<PlanWorkout> workouts) {
    this.id = id;
    this.planName = planName;
    this.duration = duration;
    this.workouts = workouts;
  }

  @JsonCreator
  public static Plan of(
      @JsonProperty("id") String id,
      @JsonProperty("name") String planName,
      @JsonProperty("duration") Period duration,
      @JsonProperty("workouts") List<PlanWorkout> workouts) {
    return new Plan(id, planName, duration, workouts);
  }

  @JsonProperty("id")
  public String id() {
    return id;
  }

  @JsonProperty("name")
  public String planName() {
    return planName;
  }

  @JsonProperty("duration")
  public Period getDuration() {
    return duration;
  }

  @JsonProperty("workouts")
  public List<PlanWorkout> getWorkouts() {
    return workouts;
  }

  public PlanRecord toPlanRecord() {
    return new PlanRecord(id, planName, duration.toString());
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (id != null) {
      result.add("\"id\": " + "\"" + id + "\"");
    }
    if (planName != null) {
      result.add("\"plan_name\": " + "\"" + planName + "\"");
    }
    if (duration != null) {
      result.add("\"duration\": " + duration);
    }
    if (workouts != null) {
      result.add("\"workouts\": " + workouts);
    }
    return result.toString();
  }

  public Plan withWorkouts(List<PlanWorkout> workouts) {
    return new Plan(id, planName, duration, workouts);
  }
}
