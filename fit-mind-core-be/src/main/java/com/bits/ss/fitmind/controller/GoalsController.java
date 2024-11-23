package com.bits.ss.fitmind.controller;

import com.bits.ss.fitmind.database.GoalRecord;
import com.bits.ss.fitmind.model.ModelAdaptor;
import com.bits.ss.fitmind.model.goals.Goal;
import com.bits.ss.fitmind.repository.GoalsRepository;
import io.vavr.collection.Stream;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoalsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(GoalsController.class);

  private final GoalsRepository goalsRepository;
  private final ModelAdaptor modelAdaptor;

  public GoalsController(GoalsRepository goalsRepository, ModelAdaptor modelAdaptor) {
    this.goalsRepository = goalsRepository;
    this.modelAdaptor = modelAdaptor;
  }

  public static String generateGoalId() {
    return "goal-" + UUID.randomUUID();
  }

  @PostMapping("/goals")
  public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
    goal = goal.withId(generateGoalId());
    GoalRecord savedGoal = goalsRepository.save(modelAdaptor.toGoalRecord(goal, null));
    return ResponseEntity.ok().body(modelAdaptor.toGoal(savedGoal));
  }

  @PostMapping("/goals/{id}")
  public ResponseEntity<Goal> updateGoal(@PathVariable("id") String id, @RequestBody Goal goal) {
    Optional<GoalRecord> oldRecord = goalsRepository.findById(id);
    if (oldRecord.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    GoalRecord oldGoalRecord = oldRecord.get();
    Goal oldGoad = modelAdaptor.toGoal(oldGoalRecord);
    Goal updatedGoal = oldGoad.mergeFrom(goal);
    GoalRecord savedRecord =
        goalsRepository.save(modelAdaptor.toGoalRecord(updatedGoal, oldGoalRecord.getWorkoutId()));
    return ResponseEntity.ok().body(modelAdaptor.toGoal(savedRecord));
  }

  @GetMapping("/goals")
  public ResponseEntity<Iterable<Goal>> getGoals() {
    Iterable<GoalRecord> records = goalsRepository.findAll();
    return ResponseEntity.ok().body(Stream.ofAll(records).map(modelAdaptor::toGoal).toJavaList());
  }

  @GetMapping("/goals/{id}")
  public ResponseEntity<Goal> getGoal(@PathVariable("id") String goalsId) {
    Optional<GoalRecord> goalRecord = goalsRepository.findById(goalsId);
    if (goalRecord.isPresent()) {
      return ResponseEntity.ok(modelAdaptor.toGoal(goalRecord.get()));
    }
    return ResponseEntity.notFound().build();
  }
}
