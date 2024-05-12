package com.bits.group13.fitnesstracker.database;

import com.bits.group13.fitnesstracker.model.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "plan")
public final class PlanRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String planName;

  public Plan toPlan() {
    return new Plan(id, planName);
  }
}
