package com.bits.group13.fitnesstracker.model;

import com.bits.group13.fitnesstracker.database.UserRecord;
import com.bits.group13.fitnesstracker.model.ApiException.ParamPatternInvalid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public final class User {
  public static final String DATE_PATTERN = "dd-MM-yyyy";
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

  private final String id;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final LocalDate dateOfBirth;

  public User(String id, String firstName, String lastName, String email, LocalDate dateOfBirth) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
  }

  @JsonCreator
  public static User of(
      @JsonProperty("id") String id,
      @JsonProperty("first_name") String firstName,
      @JsonProperty("last_name") String lastName,
      @JsonProperty("email") String email,
      @JsonProperty("date_of_birth") String dateOfBirth)
      throws ParamPatternInvalid {
    LocalDate dateOfBirthParsed = null;
    try {
      if (StringUtils.isNotEmpty(dateOfBirth)) {
        dateOfBirthParsed = LocalDate.parse(dateOfBirth, DATE_FORMATTER);
      }
    } catch (DateTimeParseException exception) {
      throw new ParamPatternInvalid("date_of_birth");
    }
    return new User(id, firstName, lastName, email, dateOfBirthParsed);
  }

  public UserRecord toUserRecord(String userId, String ownerId) {
    return new UserRecord(userId, ownerId, firstName, lastName, email, dateOfBirth);
  }

  public UserRecord toUserRecord(UserRecord oldUser, String ownerId) {
    return new UserRecord(
        Objects.requireNonNullElse(id, oldUser.getId()),
        Objects.requireNonNullElse(ownerId, oldUser.getOwnerId()),
        Objects.requireNonNullElse(firstName, oldUser.getFirstName()),
        Objects.requireNonNullElse(lastName, oldUser.getLastName()),
        Objects.requireNonNullElse(email, oldUser.getEmail()),
        Objects.requireNonNullElse(dateOfBirth, oldUser.getDateOfBirth()));
  }

  public User withEmail(String email) {
    return new User(id, firstName, lastName, email, dateOfBirth);
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("first_name")
  public String getFirstName() {
    return firstName;
  }

  @JsonProperty("last_name")
  public String getLastName() {
    return lastName;
  }

  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  @JsonProperty("date_of_birth")
  public String dateOfBirth() {
    return DATE_FORMATTER.format(dateOfBirth);
  }
}
