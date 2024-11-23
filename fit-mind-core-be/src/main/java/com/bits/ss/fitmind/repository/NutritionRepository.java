package com.bits.ss.fitmind.repository;

import com.bits.ss.fitmind.database.NutritionRecord;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface NutritionRepository extends CrudRepository<NutritionRecord, String> {
  public Optional<NutritionRecord> findById(String id);
}
