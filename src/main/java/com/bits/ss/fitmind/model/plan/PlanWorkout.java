package com.bits.ss.fitmind.model.plan;

import com.bits.ss.fitmind.model.goals.Goal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class PlanWorkout {
  private final LocalDate day;
  private final List<Goal> exercises;

  private PlanWorkout(LocalDate day, List<Goal> exercises) {
    this.day = day;
    this.exercises = exercises;
  }

  @JsonCreator
  public static PlanWorkout of(
      @JsonProperty("day") LocalDate day, @JsonProperty("exercises") List<Goal> exercises) {
    return new PlanWorkout(day, exercises);
  }

  @JsonIgnore
  public LocalDate getDay() {
    return day;
  }

  @JsonProperty("day")
  public String getDayString() {
    return day.toString();
  }

  @JsonProperty("goals")
  public List<String> getGoals() {
    return exercises.stream().map(Goal::id).collect(Collectors.toList());
  }

  @JsonIgnore
  public List<Goal> getExercises() {
    return exercises;
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (day != null) {
      result.add("\"day\": " + "\"" + day + "\"");
    }
    if (exercises != null) {
      result.add("\"exercises\": " + exercises);
    }
    return result.toString();
  }

  public PlanWorkout withDay(LocalDate day) {
    return new PlanWorkout(day, exercises);
  }

  public PlanWorkout withGoals(List<Goal> goals) {
    return new PlanWorkout(day, goals);
  }
}
