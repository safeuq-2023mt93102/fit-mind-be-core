package com.bits.group13.fitnesstracker.repository;

import com.bits.group13.fitnesstracker.database.ActivityRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<ActivityRecord, String> {
  public Optional<ActivityRecord> findByIdAndOwnerIdOrderByCreatedDesc(String id, String ownerId);

  public List<ActivityRecord> findAllByOwnerIdOrderByCreatedDesc(String ownerId);
}
