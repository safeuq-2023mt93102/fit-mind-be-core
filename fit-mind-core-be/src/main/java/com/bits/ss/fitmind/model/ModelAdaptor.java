package com.bits.ss.fitmind.model;

import com.bits.ss.fitmind.database.GoalRecord;
import com.bits.ss.fitmind.database.PlanRecord;
import com.bits.ss.fitmind.model.goals.Goal;
import com.bits.ss.fitmind.model.goals.GoalMetadata;
import com.bits.ss.fitmind.model.plan.Plan;
import com.bits.ss.fitmind.model.plan.PlanLevel;
import com.bits.ss.fitmind.model.plan.PlanTarget;
import com.bits.ss.fitmind.model.plan.PlanWorkout;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class ModelAdaptor {
  private final JsonMapper jsonMapper;

  public ModelAdaptor(JsonMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  public static Plan toPlan(PlanRecord planRecord, List<PlanWorkout> planWorkouts) {
    return Plan.of(
        planRecord.getId(),
        planRecord.getPlanName(),
        Period.parse(planRecord.getDuration()),
        planWorkouts,
        PlanLevel.valueOf(planRecord.getLevel()),
        PlanTarget.valueOf(planRecord.getTarget()));
  }

  @SneakyThrows
  public GoalRecord toGoalRecord(Goal goal, String workoutId) {
    var goalData = this.jsonMapper.writeValueAsString(goal.getData());
    if (goalData.length() > 255) {
      // language=JSON
      goalData = "{\"type\": \"EXERCISE\"}";
    }
    return new GoalRecord(
        goal.id(), workoutId, goal.getDay().toString(), goal.isCompleted(), goalData);
  }

  @SneakyThrows
  public Goal toGoal(GoalRecord goalRecord) {
    return new Goal(
        goalRecord.getGoalId(),
        LocalDate.parse(goalRecord.getGoalDay()),
        goalRecord.isGoalCompleted(),
        this.jsonMapper.readValue(goalRecord.getGoalData(), GoalMetadata.class));
  }

  public PlanRecord toPlanRecord(Plan plan) {
    return new PlanRecord(
        plan.id(),
        plan.planName(),
        plan.getLevel().toString(),
        plan.getTarget().toString(),
        plan.getDuration().toString());
  }
}
