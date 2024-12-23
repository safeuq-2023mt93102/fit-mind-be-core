package com.bits.ss.fitmind.model.activity;

import com.bits.ss.fitmind.database.ActivityRecord;
import com.bits.ss.fitmind.model.ApiException.ParamNotSet;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.time.Instant;
import java.util.StringJoiner;

public final class Activity {
  private final String id;
  private final Instant created;
  private final ActivityMetadata data;
  private final ActivitySource source;

  private Activity(String id, Instant created, ActivityMetadata data, ActivitySource source) {
    this.id = id;
    this.created = created;
    this.data = data;
    this.source = source;
  }

  @JsonCreator
  public static Activity of(
      @JsonProperty("id") String id,
      @JsonProperty("data") ActivityMetadata data,
      @JsonProperty("source") ActivitySource source)
      throws ParamNotSet {
    if (data == null) {
      throw new ParamNotSet("data");
    }
    return of(id, null, data, source);
  }

  public static Activity of(String id, Long created, ActivityMetadata data, ActivitySource source) {
    return new Activity(id, created == null ? null : Instant.ofEpochSecond(created), data, source);
  }

  public ActivityRecord toActivityRecord(JsonMapper jsonMapper, String ownerId)
      throws JsonProcessingException {
    return ActivityRecord.of(
        id,
        ownerId,
        data.getType(),
        jsonMapper.writeValueAsString(data),
        source.getType(),
        source.getProvider());
  }

  public ActivityRecord toActivityRecord(
      JsonMapper jsonMapper, String ownerId, ActivityRecord oldActivity)
      throws JsonProcessingException {
    return oldActivity.mergeFrom(toActivityRecord(jsonMapper, ownerId));
  }

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("created")
  public Long getCreated() {
    return created.getEpochSecond();
  }

  @JsonProperty("data")
  public ActivityMetadata getData() {
    return data;
  }

  @JsonProperty("source")
  public ActivitySource getSource() {
    return source;
  }

  public Activity withSource(ActivitySource source) throws ParamNotSet {
    return new Activity(id, created, data, source);
  }

  public Activity withCreated(Instant created) throws ParamNotSet {
    return new Activity(id, created, data, source);
  }

  @Override
  public String toString() {
    StringJoiner result = new StringJoiner(", ", "{", "}");
    if (id != null) {
      result.add("\"id\": " + "\"" + id + "\"");
    }
    if (data != null) {
      result.add("\"data\": " + data);
    }
    if (source != null) {
      result.add("\"source\": " + source);
    }
    return result.toString();
  }
}
