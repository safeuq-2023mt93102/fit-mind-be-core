package com.bits.ss.fitmind.model;

import com.bits.ss.fitmind.database.GoalRecord;
import com.bits.ss.fitmind.database.PlanRecord;
import com.bits.ss.fitmind.model.goals.Goal;
import com.bits.ss.fitmind.model.goals.GoalMetadata;
import com.bits.ss.fitmind.model.plan.Plan;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.LocalDate;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class ModelAdaptor {
  private final JsonMapper jsonMapper;

  public ModelAdaptor(JsonMapper jsonMapper) {
    this.jsonMapper = jsonMapper;
  }

  @SneakyThrows
  public GoalRecord toGoalRecord(Goal goal, String workoutId) {
    return new GoalRecord(
        goal.id(),
        workoutId,
        goal.getDay().toString(),
        goal.isCompleted(),
        this.jsonMapper.writeValueAsString(goal.getData()));
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
    return new PlanRecord(plan.id(), plan.planName(), plan.getDuration().toString());
  }
}
