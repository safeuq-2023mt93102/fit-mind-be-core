package com.bits.group13.fitnesstracker.database;

import com.bits.group13.fitnesstracker.model.ApiException;
import com.bits.group13.fitnesstracker.model.SourceProvider;
import com.bits.group13.fitnesstracker.model.SourceType;
import com.bits.group13.fitnesstracker.model.activity.Activity;
import com.bits.group13.fitnesstracker.model.activity.ActivityMetadata;
import com.bits.group13.fitnesstracker.model.activity.ActivitySource;
import com.bits.group13.fitnesstracker.model.activity.ActivityType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity")
public final class ActivityRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String ownerId;
  private Instant created;
  private Instant updated;

  private ActivityType activityType;
  private String metadata;
  private SourceType sourceType;
  private SourceProvider sourceProvider;

  public static ActivityRecord of(
      String id,
      String ownerId,
      ActivityType activityType,
      String metadata,
      SourceType sourceType,
      SourceProvider sourceProvider) {
    Instant currentTime = Instant.now();
    return new ActivityRecord(
        id, ownerId, currentTime, currentTime, activityType, metadata, sourceType, sourceProvider);
  }

  public Activity toActivity(JsonMapper jsonMapper)
      throws JsonProcessingException, ApiException.ParamNotSet {
    return Activity.of(
        id,
        created.getEpochSecond(),
        jsonMapper.readValue(metadata, ActivityMetadata.class),
        ActivitySource.of(sourceType, sourceProvider));
  }

  public ActivityRecord mergeFrom(ActivityRecord oldActivity) {
    Instant updatedTime = Instant.now();
    return new ActivityRecord(
        Objects.requireNonNullElse(id, oldActivity.getId()),
        Objects.requireNonNullElse(ownerId, oldActivity.getOwnerId()),
        oldActivity.getCreated(),
        updatedTime,
        Objects.requireNonNullElse(activityType, oldActivity.getActivityType()),
        Objects.requireNonNullElse(metadata, oldActivity.getMetadata()),
        Objects.requireNonNullElse(sourceType, oldActivity.getSourceType()),
        Objects.requireNonNullElse(sourceProvider, oldActivity.getSourceProvider()));
  }
}
