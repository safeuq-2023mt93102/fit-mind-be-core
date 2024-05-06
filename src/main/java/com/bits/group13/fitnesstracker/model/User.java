package com.bits.group13.fitnesstracker.model;

import com.bits.group13.fitnesstracker.database.UserRecord;
import com.bits.group13.fitnesstracker.model.ApiException.ParamPatternInvalid;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

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
    LocalDate dateOfBirthParsed;
    try {
      dateOfBirthParsed = LocalDate.parse(dateOfBirth, DATE_FORMATTER);
    } catch (DateTimeParseException exception) {
      throw new ParamPatternInvalid("date_of_birth");
    }
    return new User(id, firstName, lastName, email, dateOfBirthParsed);
  }

  public UserRecord toUserRecord() {
    return new UserRecord(id, firstName, lastName, email, dateOfBirth);
  }

  public UserRecord toUserRecord(UserRecord oldUser) {
    return new UserRecord(
        Objects.requireNonNullElse(id, oldUser.getId()),
        Objects.requireNonNullElse(firstName, oldUser.getFirstName()),
        Objects.requireNonNullElse(lastName, oldUser.getLastName()),
        Objects.requireNonNullElse(email, oldUser.getEmail()),
        Objects.requireNonNullElse(dateOfBirth, oldUser.getDateOfBirth()));
  }

  public User withEmail(String email) {
    return new User(id, firstName, lastName, email, dateOfBirth);
  }

  @JsonProperty("id")
  public String id() {
    return id;
  }

  @JsonProperty("first_name")
  public String firstName() {
    return firstName;
  }

  @JsonProperty("last_name")
  public String lastName() {
    return lastName;
  }

  @JsonProperty("email")
  public String email() {
    return email;
  }

  @JsonProperty("date_of_birth")
  public String dateOfBirth() {
    return DATE_FORMATTER.format(dateOfBirth);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (User) obj;
    return Objects.equals(this.id, that.id)
        && Objects.equals(this.firstName, that.firstName)
        && Objects.equals(this.lastName, that.lastName)
        && Objects.equals(this.email, that.email)
        && Objects.equals(this.dateOfBirth, that.dateOfBirth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, email, dateOfBirth);
  }

  @Override
  public String toString() {
    return "User["
        + "id="
        + id
        + ", "
        + "firstName="
        + firstName
        + ", "
        + "lastName="
        + lastName
        + ", "
        + "email="
        + email
        + ", "
        + "dateOfBirth="
        + dateOfBirth
        + ']';
  }
}
