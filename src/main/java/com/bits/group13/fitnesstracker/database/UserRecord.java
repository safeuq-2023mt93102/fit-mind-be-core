package com.bits.group13.fitnesstracker.database;

import com.bits.group13.fitnesstracker.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@IdClass(UserRecord.PrimaryKey.class)
public final class UserRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String firstName;
  private String lastName;

  @Column(unique = true, nullable = false)
  private String email;

  private LocalDate dateOfBirth;

  public User toUser() {
    return new User(id, firstName, lastName, email, dateOfBirth);
  }

  public static class PrimaryKey implements Serializable {
    private String id;
    private String email;
  }
}
