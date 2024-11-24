package com.bits.ss.fitmind.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "plan")
public final class PlanRecord {
  @Id private String id;

  private String planName;
  private String level;
  private String target;
  private String duration;
}
