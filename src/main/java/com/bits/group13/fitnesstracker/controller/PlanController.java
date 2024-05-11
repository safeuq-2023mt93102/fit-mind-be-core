package com.bits.group13.fitnesstracker.controller;

import com.bits.group13.fitnesstracker.database.PlanRecord;
import com.bits.group13.fitnesstracker.model.ApiException;
import com.bits.group13.fitnesstracker.model.Plan;
import com.bits.group13.fitnesstracker.repository.PlanRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PlanController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanController.class);

    private final PlanRepository planRepository;

    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @PostMapping("/plan")
    public ResponseEntity<?> createPlan(@RequestBody Plan plan) throws ApiException {
        if (StringUtils.isEmpty(plan.planName())) {
            throw new ApiException.ParamEmpty("planName");
        }
        PlanRecord savedPlan = planRepository.save(plan.toPlanRecord());
        return ResponseEntity.ok().body(savedPlan);
    }

    @PostMapping("/plan/{id}")
    public ResponseEntity<?> updatePlan(@PathVariable("id") String id, @RequestBody Plan plan) {
        Optional<PlanRecord> oldRecord = planRepository.findById(id);
        if (oldRecord.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PlanRecord savedRecord = planRepository.save(plan.toPlanRecord());
        return ResponseEntity.ok().body(savedRecord);
    }

    @GetMapping("/plan")
    public ResponseEntity<Iterable<?>> getPlan() {
        Iterable<PlanRecord> records = planRepository.findAll();
        return ResponseEntity.ok().body(records);
    }

    @GetMapping("/plan/{id}")
    public ResponseEntity<Plan> getPlan(@PathVariable("id") String planId) {
        return planRepository
                .findById(planId)
                .map(userRecord -> ResponseEntity.ok(userRecord.toPlan()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
