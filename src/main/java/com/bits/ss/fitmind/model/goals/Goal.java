package com.bits.ss.fitmind.model.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Goal {
  private final String id;
  private final LocalDate day;
  private final Boolean completed;
  private final GoalMetadata data;

  @JsonCreator
  public Goal(
      @JsonProperty("id") String id,
      @JsonProperty("day") LocalDate day,
      @JsonProperty("completed") Boolean completed,
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

  public Goal withId(String id) {
    return new Goal(id, day, completed, data);
  }

  public Goal withDay(LocalDate localDate) {
    return new Goal(id, localDate, completed, data);
  }

  public Goal mergeFrom(Goal newGoal) {
    return new Goal(id, day, newGoal.completed != null ? newGoal.completed : completed, data);
  }
}
