package com.bits.ss.fitmind.repository;

import com.bits.ss.fitmind.database.PlanRecord;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<PlanRecord, String> {
  public Optional<PlanRecord> findById(String id);
}
