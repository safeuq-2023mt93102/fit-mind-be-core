package com.bits.group13.fitnesstracker.repository;

import com.bits.group13.fitnesstracker.database.UserRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRecord, UserRecord.PrimaryKey> {
  public Optional<UserRecord> findByIdAndOwnerId(String id, String ownerId);

  public List<UserRecord> findAllByOwnerId(String ownerId);
}
