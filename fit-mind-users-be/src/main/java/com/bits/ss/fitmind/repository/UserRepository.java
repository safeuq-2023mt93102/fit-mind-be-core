package com.bits.ss.fitmind.repository;

import com.bits.ss.fitmind.database.UserRecord;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserRecord, UserRecord.PrimaryKey> {
  public Optional<UserRecord> findByIdAndOwnerId(String id, String ownerId);

  public List<UserRecord> findAllByOwnerId(String ownerId);
}
