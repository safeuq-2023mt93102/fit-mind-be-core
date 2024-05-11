package com.bits.group13.fitnesstracker.repository;

import com.bits.group13.fitnesstracker.database.GoalRecord;
import com.bits.group13.fitnesstracker.database.PlanRecord;
import com.bits.group13.fitnesstracker.database.UserRecord;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<PlanRecord, String> {
    public Optional<PlanRecord> findById(String id);
}
