package com.bits.group13.fitnesstracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @PostMapping("/users")
  public ResponseEntity<?> createUser() {
    // TODO: Implementation
    return ResponseEntity.ok().body("{}");
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<?> createUser(@PathVariable String id) {
    // TODO: Implementation
    LOGGER.info("Get user: {}", id);
    return ResponseEntity.notFound().build();
  }
}
