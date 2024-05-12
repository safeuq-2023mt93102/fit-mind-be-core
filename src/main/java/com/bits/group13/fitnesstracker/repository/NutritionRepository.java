package com.bits.group13.fitnesstracker.repository;

import com.bits.group13.fitnesstracker.database.NutritionRecord;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface NutritionRepository extends CrudRepository<NutritionRecord, String> {
  public Optional<NutritionRecord> findById(String id);
}
