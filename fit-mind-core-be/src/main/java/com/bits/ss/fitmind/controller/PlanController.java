package com.bits.ss.fitmind.controller;

import static com.bits.ss.fitmind.controller.GoalsController.generateGoalId;

import com.bits.ss.fitmind.database.GoalRecord;
import com.bits.ss.fitmind.database.PlanRecord;
import com.bits.ss.fitmind.model.ModelAdaptor;
import com.bits.ss.fitmind.model.goals.Goal;
import com.bits.ss.fitmind.model.plan.Plan;
import com.bits.ss.fitmind.model.plan.PlanRequest;
import com.bits.ss.fitmind.model.plan.PlanWorkout;
import com.bits.ss.fitmind.repository.GoalsRepository;
import com.bits.ss.fitmind.repository.PlanRepository;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan")
public class PlanController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

  private final PlanRepository planRepository;
  private final GoalsRepository goalsRepository;
  private final JsonMapper jsonMapper;
  private final ModelAdaptor modelAdaptor;

  public PlanController(
      PlanRepository planRepository,
      GoalsRepository goalsRepository,
      JsonMapper jsonMapper,
      ModelAdaptor modelAdaptor) {
    this.planRepository = planRepository;
    this.goalsRepository = goalsRepository;
    this.jsonMapper = jsonMapper;
    this.modelAdaptor = modelAdaptor;
  }

  @PostMapping("/")
  public ResponseEntity<?> createPlan(@RequestBody PlanRequest planRequest) throws IOException {
    Plan plan = generatePlan(planRequest);
    PlanRecord savedPlan = planRepository.save(modelAdaptor.toPlanRecord(plan));
    plan = savedPlan.toPlan(plan.getWorkouts());
    Plan finalPlan = plan;
    List<GoalRecord> allGoals =
        plan.getWorkouts().stream()
            .flatMap(planWorkout -> planWorkout.getExercises().stream())
            .map(goal -> modelAdaptor.toGoalRecord(goal, finalPlan.id()))
            .collect(Collectors.toList());
    Iterable<GoalRecord> goalRecords = goalsRepository.saveAll(allGoals);
    List<Goal> savedGoals = Stream.ofAll(goalRecords).map(modelAdaptor::toGoal).toJavaList();
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
    String planId = "plan-" + UUID.randomUUID();
    Plan readPlan = readPlan(planRequest).withId(planId);
    LocalDate localDate = LocalDate.now();
    List<PlanWorkout> workouts = new ArrayList<>();
    for (PlanWorkout workout : readPlan.getWorkouts()) {
      List<Goal> goals = new ArrayList<>();
      for (Goal goal : workout.getExercises()) {
        goals.add(goal.withId(generateGoalId()).withDay(localDate));
      }
      workouts.add(workout.withDay(localDate).withGoals(goals));
      localDate = localDate.plusDays(1);
    }
    return readPlan.withWorkouts(workouts);
  }

  private Plan readPlan(PlanRequest planRequest) throws IOException {
    String templatePath =
        switch (planRequest.getLevel()) {
          case BEGINNER ->
              switch (planRequest.getTarget()) {
                case GAIN_WEIGHT -> "workout_template/beginner-gain-weight.json";
                case LOSE_WEIGHT -> "workout_template/beginner-lose-weight.json";
              };
          case ADVANCED ->
              switch (planRequest.getTarget()) {
                case GAIN_WEIGHT -> "workout_template/advanced-gain-weight.json";
                case LOSE_WEIGHT -> "workout_template/advanced-lose-weight.json";
              };
        };

    return jsonMapper.readValue(ClassLoader.getSystemResourceAsStream(templatePath), Plan.class);
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

  @GetMapping("/")
  public ResponseEntity<List<Plan>> listPlans() {
    List<Plan> records =
        Stream.ofAll(planRepository.findAll())
            .map(planRecord -> planRecord.toPlan(List.of()))
            .toJavaList();
    return ResponseEntity.ok().body(records);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Plan> getPlan(@PathVariable("id") String planId) {
    Optional<PlanRecord> planRecordOptional = planRepository.findById(planId);
    if (planRecordOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    PlanRecord planRecord = planRecordOptional.get();
    Plan plan = planRecord.toPlan(getAllWorkoutsForPlan(planRecord.getId()));
    return ResponseEntity.ok(plan);
  }

  private List<PlanWorkout> getAllWorkoutsForPlan(String planId) {
    List<GoalRecord> allGoalRecords = goalsRepository.findByWorkoutId(planId);
    Seq<Stream<Goal>> goalsByDay =
        Stream.ofAll(allGoalRecords).map(modelAdaptor::toGoal).groupBy(Goal::getDay).values();
    return goalsByDay.toStream().map(PlanController::toPlanWorkout).toJavaList();
  }

  private static PlanWorkout toPlanWorkout(Stream<Goal> goals) {
    return PlanWorkout.of(goals.head().getDay(), goals.toJavaList());
  }
}
