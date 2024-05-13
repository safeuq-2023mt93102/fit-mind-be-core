package com.bits.group13.fitnesstracker.model.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import java.util.StringJoiner;

public class ExerciseGoal implements GoalMetadata {
  private final ExerciseType exercise;
  private final Long sets;
  private final Long repetitions;
  private final Duration duration;

  private ExerciseGoal(ExerciseType type, Long sets, Long repetitions, Duration duration) {
    this.exercise = type;
    this.sets = sets;
    this.repetitions = repetitions;
    this.duration = duration;
  }

  @JsonCreator
  public static ExerciseGoal of(
      @JsonProperty("exercise") ExerciseType type,
      @JsonProperty("sets") Long sets,
      @JsonProperty("repetitions") Long repetitions,
      @JsonProperty("duration") Duration duration) {
    return new ExerciseGoal(type, sets, repetitions, duration);
  }

  @JsonProperty("exercise")
  public ExerciseType getExercise() {
    return exercise;
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

  @Override
  public GoalType getType() {
    return GoalType.EXERCISE;
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (exercise != null) {
      result.add("\"exercise\": " + "\"" + exercise + "\"");
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
    return result.toString();
  }
}
