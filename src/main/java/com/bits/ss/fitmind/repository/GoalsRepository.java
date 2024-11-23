package com.bits.ss.fitmind.repository;

import com.bits.ss.fitmind.database.GoalRecord;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GoalsRepository extends CrudRepository<GoalRecord, String> {
  public List<GoalRecord> findByWorkoutId(String workoutId);
}
