package com.bits.group13.fitnesstracker.controller;

import com.bits.group13.fitnesstracker.database.NutritionRecord;
import com.bits.group13.fitnesstracker.model.ApiException;
import com.bits.group13.fitnesstracker.model.Nutrition;
import com.bits.group13.fitnesstracker.repository.NutritionRepository;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NutritionController {
  private static final Logger LOGGER = LoggerFactory.getLogger(NutritionController.class);

  private final NutritionRepository nutritionRepository;

  public NutritionController(NutritionRepository nutritionRepository) {
    this.nutritionRepository = nutritionRepository;
  }

  @PostMapping("/nutrition")
  public ResponseEntity<?> createNutrition(@RequestBody Nutrition nutrition) throws ApiException {
    if (StringUtils.isEmpty(nutrition.nutritionType())) {
      throw new ApiException.ParamNotSet("nutritionType");
    }
    NutritionRecord savedNutrition = nutritionRepository.save(nutrition.toNutritionRecord());
    return ResponseEntity.ok().body(savedNutrition);
  }

  @PostMapping("/nutrition/{id}")
  public ResponseEntity<?> updateNutrition(
      @PathVariable("id") String id, @RequestBody Nutrition nutrition) {
    Optional<NutritionRecord> oldRecord = nutritionRepository.findById(id);
    if (oldRecord.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    NutritionRecord savedRecord = nutritionRepository.save(nutrition.toNutritionRecord());
    return ResponseEntity.ok().body(savedRecord);
  }

  @GetMapping("/nutrition")
  public ResponseEntity<Iterable<?>> getNutrition() {
    Iterable<NutritionRecord> records = nutritionRepository.findAll();
    return ResponseEntity.ok().body(records);
  }

  @GetMapping("/nutrition/{id}")
  public ResponseEntity<Nutrition> getNutrition(@PathVariable("id") String id) {
    return nutritionRepository
        .findById(id)
        .map(nutritionRecord -> ResponseEntity.ok(nutritionRecord.toNutrition()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
