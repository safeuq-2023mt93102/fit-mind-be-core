package com.bits.group13.fitnesstracker.model.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import java.util.List;
import java.util.StringJoiner;

public class ExerciseGoal implements GoalMetadata {
  private final ExerciseType type;
  private final Long sets;
  private final Long repetitions;
  private final Duration duration;
  private final List<ExerciseGoal> exercises;

  private ExerciseGoal(
      ExerciseType type,
      Long sets,
      Long repetitions,
      Duration duration,
      List<ExerciseGoal> exercises) {
    this.type = type;
    this.sets = sets;
    this.repetitions = repetitions;
    this.duration = duration;
    this.exercises = exercises;
  }

  @JsonCreator
  public static ExerciseGoal of(
      @JsonProperty("type") ExerciseType type,
      @JsonProperty("sets") Long sets,
      @JsonProperty("repetitions") Long repetitions,
      @JsonProperty("duration") Duration duration,
      @JsonProperty("exercises") List<ExerciseGoal> exercises) {
    return new ExerciseGoal(type, sets, repetitions, duration, exercises);
  }

  @JsonProperty("type")
  public ExerciseType getType() {
    return type;
  }

  @JsonProperty("sets")
  public Long getSets() {
    return sets;
  }

  @JsonProperty("repetitions")
  public Long getRepetitions() {
    return repetitions;
  }

  @JsonProperty("duration")
  public Duration getDuration() {
    return duration;
  }

  @JsonProperty("exercises")
  public List<ExerciseGoal> getExercises() {
    return exercises;
  }

  @Override
  public GoalType getActivityType() {
    return GoalType.EXERCISE;
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (type != null) {
      result.add("\"type\": " + "\"" + type + "\"");
    }
    if (sets != null) {
      result.add("\"sets\": " + sets);
    }
    if (repetitions != null) {
      result.add("\"repetitions\": " + repetitions);
    }
    if (duration != null) {
      result.add("\"duration\": " + duration);
    }
    if (exercises != null) {
      result.add("\"exercises\": " + exercises);
    }
    return result.toString();
  }
}
