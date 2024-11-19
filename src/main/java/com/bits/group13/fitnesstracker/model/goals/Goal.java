package com.bits.group13.fitnesstracker.model.goals;

import com.bits.group13.fitnesstracker.database.GoalRecord;
import com.bits.group13.fitnesstracker.model.plan.Plan;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Goal {
  private final String id;
  private final LocalDate day;
  private final boolean completed;
  private final GoalMetadata data;

  public static void main(String[] args) throws IOException {
    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
    Plan plan =
        jsonMapper.readValue(
            ClassLoader.getSystemResourceAsStream("workout_template/beginner-lose-weight.json"),
            Plan.class);
    System.out.println(plan);
    System.out.println(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(plan));
  }

  @JsonCreator
  public Goal(
      @JsonProperty("id") String id,
      @JsonProperty("day") LocalDate day,
      @JsonProperty("completed") boolean completed,
      @JsonProperty("data") GoalMetadata data) {
    this.id = id;
    this.day = day;
    this.completed = completed;
    this.data = data;
  }

  @JsonProperty("id")
  public String id() {
    return id;
  }

  @JsonProperty("day")
  public String getDayString() {
    return day.toString();
  }

  @JsonIgnore
  public LocalDate getDay() {
    return day;
  }

  @JsonProperty("completed")
  public boolean isCompleted() {
    return completed;
  }

  @JsonProperty("data")
  public GoalMetadata getData() {
    return data;
  }

  public GoalRecord toGoalRecord(String workoutId, JsonMapper jsonMapper)
      throws JsonProcessingException {
    return new GoalRecord(
        id, workoutId, day.toString(), completed, jsonMapper.writeValueAsString(data));
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (id != null) {
      result.add("\"id\": " + "\"" + id + "\"");
    }
    if (day != null) {
      result.add("\"day\": " + "\"" + day + "\"");
    }
    result.add("\"completed\": " + completed);
    if (data != null) {
      result.add("\"data\": " + data);
    }
    return result.toString();
  }

  public Goal withDay(LocalDate localDate) {
    return new Goal(id, localDate, completed, data);
  }
}
