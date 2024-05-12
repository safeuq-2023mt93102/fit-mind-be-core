package com.bits.group13.fitnesstracker.repository;

import com.bits.group13.fitnesstracker.database.GoalRecord;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface GoalsRepository extends CrudRepository<GoalRecord, String> {
  public Optional<GoalRecord> findById(String id);
}
