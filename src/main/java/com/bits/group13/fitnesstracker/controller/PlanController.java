package com.bits.group13.fitnesstracker.controller;

import com.bits.group13.fitnesstracker.database.GoalRecord;
import com.bits.group13.fitnesstracker.database.PlanRecord;
import com.bits.group13.fitnesstracker.model.goals.Goal;
import com.bits.group13.fitnesstracker.model.plan.Plan;
import com.bits.group13.fitnesstracker.model.plan.PlanRequest;
import com.bits.group13.fitnesstracker.model.plan.PlanWorkout;
import com.bits.group13.fitnesstracker.repository.GoalsRepository;
import com.bits.group13.fitnesstracker.repository.PlanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlanController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

  private final PlanRepository planRepository;
  private final GoalsRepository goalsRepository;
  private final JsonMapper jsonMapper;

  public PlanController(
      PlanRepository planRepository, GoalsRepository goalsRepository, JsonMapper jsonMapper) {
    this.planRepository = planRepository;
    this.goalsRepository = goalsRepository;
    this.jsonMapper = jsonMapper;
  }

  @PostMapping("/plan")
  public ResponseEntity<?> createPlan(@RequestBody PlanRequest planRequest) throws IOException {
    Plan plan = generatePlan(planRequest);
    PlanRecord savedPlan = planRepository.save(plan.toPlanRecord());
    plan = savedPlan.toPlan(plan.getWorkouts());
    Plan finalPlan = plan;
    List<GoalRecord> allGoals =
        plan.getWorkouts().stream()
            .flatMap(planWorkout -> planWorkout.getExercises().stream())
            .map(
                goal -> {
                  try {
                    return goal.toGoalRecord(finalPlan.id(), jsonMapper);
                  } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                  }
                })
            .collect(Collectors.toList());
    Iterable<GoalRecord> goalRecords = goalsRepository.saveAll(allGoals);
    List<Goal> savedGoals =
        StreamSupport.stream(goalRecords.spliterator(), false)
            .map(
                goalRecord -> {
                  try {
                    return goalRecord.toGoal(jsonMapper);
                  } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                  }
                })
            .toList();
    Map<LocalDate, List<Goal>> goalsByDay =
        savedGoals.stream()
            .collect(
                Collectors.groupingBy(
                    Goal::getDay, TreeMap::new, Collectors.toCollection(ArrayList::new)));
    List<PlanWorkout> planWorkouts =
        goalsByDay.values().stream()
            .map(goals -> PlanWorkout.of(goals.iterator().next().getDay(), goals))
            .collect(Collectors.toList());
    return ResponseEntity.ok().body(savedPlan.toPlan(planWorkouts));
  }

  private Plan generatePlan(PlanRequest planRequest) throws IOException {
    Plan readPlan = readPlan(planRequest);
    LocalDate localDate = LocalDate.now();
    List<PlanWorkout> workouts = new ArrayList<>();
    for (PlanWorkout workout : readPlan.getWorkouts()) {
      List<Goal> goals = new ArrayList<>();
      for (Goal goal : workout.getExercises()) {
        goals.add(goal.withDay(localDate));
      }
      workouts.add(workout.withDay(localDate).withGoals(goals));
      localDate = localDate.plusDays(1);
    }
    return readPlan.withWorkouts(workouts);
  }

  private Plan readPlan(PlanRequest planRequest) throws IOException {
    switch (planRequest.getLevel()) {
      case BEGINNER -> {
        switch (planRequest.getTarget()) {
          case GAIN_WEIGHT -> {
            return jsonMapper.readValue(
                ClassLoader.getSystemResourceAsStream("workout_template/beginner-gain-weight.json"),
                Plan.class);
          }
          case LOSE_WEIGHT -> {}
        }
      }
      case ADVANCED -> {
        switch (planRequest.getTarget()) {
          case GAIN_WEIGHT -> {}
          case LOSE_WEIGHT -> {}
        }
      }
    }
    throw new IllegalArgumentException();
  }

  //  @PostMapping("/plan/{id}")
  //  public ResponseEntity<?> updatePlan(@PathVariable("id") String id, @RequestBody Plan plan) {
  //    Optional<PlanRecord> oldRecord = planRepository.findById(id);
  //    if (oldRecord.isEmpty()) {
  //      return ResponseEntity.notFound().build();
  //    }
  //    PlanRecord savedRecord = planRepository.save(plan.toPlanRecord());
  //    return ResponseEntity.ok().body(savedRecord);
  //  }

  @GetMapping("/plan")
  public ResponseEntity<Iterable<?>> getPlan() {
    Iterable<PlanRecord> records = planRepository.findAll();
    return ResponseEntity.ok().body(records);
  }

  @GetMapping("/plan/{id}")
  public ResponseEntity<Plan> getPlan(@PathVariable("id") String planId) {
    return planRepository
        .findById(planId)
        .map(userRecord -> ResponseEntity.ok(userRecord.toPlan(List.of())))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
