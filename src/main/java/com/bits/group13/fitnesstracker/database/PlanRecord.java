package com.bits.group13.fitnesstracker.database;

import com.bits.group13.fitnesstracker.model.plan.Plan;
import com.bits.group13.fitnesstracker.model.plan.PlanWorkout;
import jakarta.persistence.*;
import java.time.Period;
import java.util.List;
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
  private String duration;

  public Plan toPlan(List<PlanWorkout> planWorkouts) {
    return Plan.of(id, planName, Period.parse(duration), planWorkouts);
  }
}
