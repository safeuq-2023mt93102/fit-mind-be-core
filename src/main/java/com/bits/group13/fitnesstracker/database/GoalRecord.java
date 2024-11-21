package com.bits.group13.fitnesstracker.database;

import com.bits.group13.fitnesstracker.model.goals.Goal;
import com.bits.group13.fitnesstracker.model.goals.GoalMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "goals")
public final class GoalRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String goalId;

  @Column(nullable = true)
  private String workoutId;

  private String goalDay;
  private boolean goalCompleted;
  private String goalData;

  public Goal toGoal(JsonMapper jsonMapper) throws JsonProcessingException {
    return new Goal(
        goalId,
        LocalDate.parse(goalDay),
        goalCompleted,
        jsonMapper.readValue(goalData, GoalMetadata.class));
  }
}
