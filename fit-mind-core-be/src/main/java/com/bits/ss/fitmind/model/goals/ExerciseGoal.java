package com.bits.ss.fitmind.model.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import java.util.List;
import java.util.StringJoiner;

public class ExerciseGoal implements GoalMetadata {
  private final ExerciseType exercise;
  private final Long sets;
  private final Long repetitions;
  private final Duration duration;
  private final List<GoalMetadata> exercises;
  private final String weight;
  private final String id;

  private ExerciseGoal(
          ExerciseType exercise,
          Long sets,
          Long repetitions,
          Duration duration,
          List<GoalMetadata> exercises,
          String weight, String id) {
    this.exercise = exercise;
    this.sets = sets;
    this.repetitions = repetitions;
    this.duration = duration;
    this.exercises = exercises;
    this.weight = weight;
      this.id = id;
  }

  @JsonCreator
  public static ExerciseGoal of(
          @JsonProperty("exercise") ExerciseType exercise,
          @JsonProperty("sets") Long sets,
          @JsonProperty("repetitions") Long repetitions,
          @JsonProperty("duration") Duration duration,
          @JsonProperty("exercises") List<GoalMetadata> exercises,
          @JsonProperty("weight") String weight,
          @JsonProperty("id") String id) {
    return new ExerciseGoal(exercise, sets, repetitions, duration, exercises, weight, id);
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

  @JsonProperty("exercises")
  public List<GoalMetadata> getExercises() {
    return exercises;
  }

  @JsonProperty("weight")
  public String getWeight() {
    return weight;
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
    if (exercises != null) {
      result.add("\"exercises\": " + exercises);
    }
    if (weight != null) {
      result.add("\"weight\": " + weight);
    }
    return result.toString();
  }
}
