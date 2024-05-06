package com.bits.group13.fitnesstracker.controller;

import com.bits.group13.fitnesstracker.database.UserRecord;
import com.bits.group13.fitnesstracker.model.ApiException;
import com.bits.group13.fitnesstracker.model.ApiException.ParamEmpty;
import com.bits.group13.fitnesstracker.model.ApiException.ParamNotUnique;
import com.bits.group13.fitnesstracker.model.User;
import com.bits.group13.fitnesstracker.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/users")
  public ResponseEntity<?> createUser(@RequestBody User user) throws ApiException {
    if (StringUtils.isEmpty(user.firstName())) {
      throw new ParamEmpty("first_name");
    }
    if (StringUtils.isEmpty(user.lastName())) {
      throw new ParamEmpty("last_name");
    }
    if (user.dateOfBirth() == null) {
      throw new ParamEmpty("date_of_birth");
    }
    if (StringUtils.isEmpty(user.email())) {
      throw new ParamEmpty("email");
    }
    try {
      UserRecord savedUser = userRepository.save(user.toUserRecord());
      return ResponseEntity.ok().body(savedUser);
    } catch (DataIntegrityViolationException exception) {
      throw new ParamNotUnique("email");
    }
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUser(@PathVariable("id") String userId) {
    return userRepository
        .findById(userId)
        .map(userRecord -> ResponseEntity.ok(userRecord.toUser()))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
    Optional<UserRecord> oldUserOptional = userRepository.findById(id);
    if (oldUserOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    UserRecord oldUser = oldUserOptional.get();
    if (StringUtils.isNotEmpty(user.email()) && !Objects.equals(user.email(), oldUser.getEmail())) {
      return ResponseEntity.badRequest().body(new ApiException.ParamNotEditable("email"));
    }
    try {
      UserRecord savedUser = userRepository.save(user.toUserRecord(oldUser));
      return ResponseEntity.ok().body(savedUser);
    } catch (DataIntegrityViolationException exception) {
      return ResponseEntity.badRequest().body(new ParamNotUnique("email"));
    }
  }
}
