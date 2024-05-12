package com.bits.group13.fitnesstracker.controller;

import com.bits.group13.fitnesstracker.database.GoalRecord;
import com.bits.group13.fitnesstracker.model.ApiException;
import com.bits.group13.fitnesstracker.model.Goal;
import com.bits.group13.fitnesstracker.repository.GoalsRepository;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoalsController {
  private static final Logger LOGGER = LoggerFactory.getLogger(GoalsController.class);

  private final GoalsRepository goalsRepository;

  public GoalsController(GoalsRepository goalsRepository) {
    this.goalsRepository = goalsRepository;
  }

  @PostMapping("/goals")
  public ResponseEntity<?> createGoal(@RequestBody Goal goal) throws ApiException {
    if (StringUtils.isEmpty(goal.type())) {
      throw new ApiException.ParamNotSet("type");
    }
    GoalRecord savedGoal = goalsRepository.save(goal.toGoalRecord());
    return ResponseEntity.ok().body(savedGoal);
  }

  @PostMapping("/goals/{id}")
  public ResponseEntity<?> updateGoal(@PathVariable("id") String id, @RequestBody Goal goal) {
    Optional<GoalRecord> oldRecord = goalsRepository.findById(id);
    if (oldRecord.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    GoalRecord savedRecord = goalsRepository.save(goal.toGoalRecord());
    return ResponseEntity.ok().body(savedRecord);
  }

  @GetMapping("/goals")
  public ResponseEntity<Iterable<?>> getGoals() {
    Iterable<GoalRecord> records = goalsRepository.findAll();
    return ResponseEntity.ok().body(records);
  }

  @GetMapping("/goals/{id}")
  public ResponseEntity<Goal> getGoal(@PathVariable("id") String goalsId) {
    return goalsRepository
        .findById(goalsId)
        .map(userRecord -> ResponseEntity.ok(userRecord.toGoal()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
