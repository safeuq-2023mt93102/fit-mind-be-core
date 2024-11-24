package com.bits.ss.fitmind.model.plan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.time.Period;
import java.util.List;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Plan {
  private final String id;
  private final String planName;
  private final PlanLevel level;
  private final PlanTarget target;
  private final Period duration;
  private final List<PlanWorkout> workouts;

  private Plan(
      String id,
      String planName,
      PlanLevel level,
      PlanTarget target,
      Period duration,
      List<PlanWorkout> workouts) {
    this.id = id;
    this.planName = planName;
    this.level = level;
    this.target = target;
    this.duration = duration;
    this.workouts = workouts;
  }

  public static void main(String[] args) throws IOException {
    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
    Plan plan =
        jsonMapper.readValue(
            Plan.class
                .getClassLoader()
                .getResourceAsStream("workout_template/beginner-lose-weight.json"),
            Plan.class);
    System.out.println(jsonMapper.writeValueAsString(plan));
  }

  @JsonCreator
  public static Plan of(
      @JsonProperty("id") String id,
      @JsonProperty("name") String planName,
      @JsonProperty("duration") Period duration,
      @JsonProperty("workouts") List<PlanWorkout> workouts,
      @JsonProperty("level") PlanLevel level,
      @JsonProperty("target") PlanTarget target) {
    return new Plan(id, planName, level, target, duration, workouts);
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

  @JsonProperty("level")
  public PlanLevel getLevel() {
    return level;
  }

  @JsonProperty("target")
  public PlanTarget getTarget() {
    return target;
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (id() != null) {
      result.add("\"id\": " + "\"" + id() + "\"");
    }
    if (planName() != null) {
      result.add("\"plan_name\": " + "\"" + planName() + "\"");
    }
    if (getDuration() != null) {
      result.add("\"duration\": " + "\"" + getDuration() + "\"");
    }
    if (getWorkouts() != null) {
      result.add("\"workouts\": " + getWorkouts());
    }
    return result.toString();
  }

  public Plan withId(String id) {
    return of(id, planName(), getDuration(), getWorkouts(), getLevel(), getTarget());
  }

  public Plan withLevel(PlanLevel level) {
    return of(id(), planName(), getDuration(), getWorkouts(), level, getTarget());
  }

  public Plan withTarget(PlanTarget target) {
    return of(id(), planName(), getDuration(), getWorkouts(), getLevel(), target);
  }

  public Plan withWorkouts(List<PlanWorkout> workouts) {
    return of(id(), planName(), getDuration(), workouts, getLevel(), getTarget());
  }
}
