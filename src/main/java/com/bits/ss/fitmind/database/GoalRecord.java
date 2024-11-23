package com.bits.ss.fitmind.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  @Id private String goalId;

  @Column(nullable = true)
  private String workoutId;

  private String goalDay;
  private boolean goalCompleted;
  private String goalData;
}
