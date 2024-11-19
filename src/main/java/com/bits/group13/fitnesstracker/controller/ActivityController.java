package com.bits.group13.fitnesstracker.controller;

import com.bits.group13.fitnesstracker.database.ActivityRecord;
import com.bits.group13.fitnesstracker.model.ApiException;
import com.bits.group13.fitnesstracker.model.ApiException.ParamNotEditable;
import com.bits.group13.fitnesstracker.model.ApiException.ParamNotSet;
import com.bits.group13.fitnesstracker.model.ApiException.ParamUnexpected;
import com.bits.group13.fitnesstracker.model.ApiException.ParamValueInvalid;
import com.bits.group13.fitnesstracker.model.SourceType;
import com.bits.group13.fitnesstracker.model.activity.Activity;
import com.bits.group13.fitnesstracker.model.activity.ActivitySource;
import com.bits.group13.fitnesstracker.model.activity.metadata.CyclingActivity;
import com.bits.group13.fitnesstracker.model.activity.metadata.WalkingActivity;
import com.bits.group13.fitnesstracker.repository.ActivityRepository;
import com.bits.group13.fitnesstracker.security.SecurityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

  private final JsonMapper jsonMapper;
  private final ActivityRepository activityRepository;

  public ActivityController(JsonMapper jsonMapper, ActivityRepository activityRepository) {
    this.jsonMapper = jsonMapper;
    this.activityRepository = activityRepository;
  }

  @PostMapping(value = {"", "/"})
  public ResponseEntity<Activity> createActivity(
      @RequestBody Activity activity, Principal principal)
      throws ApiException, JsonProcessingException {
    if (activity.getData() == null) {
      throw new ParamNotSet("data");
    }
    switch (activity.getData().getActivityType()) {
      case WALKING -> {
        WalkingActivity walkingActivity = (WalkingActivity) activity.getData();
        if (walkingActivity.getSteps() <= 0) {
          throw new ParamValueInvalid("data.steps");
        }
      }
      case CYCLING -> {
        CyclingActivity cyclingActivity = (CyclingActivity) activity.getData();
        if (cyclingActivity.getDistance() <= 0) {
          throw new ParamValueInvalid("data.distance");
        }
        if (cyclingActivity.getUnit() == null) {
          throw new ParamNotSet("data.unit");
        }
      }
      case CALORIES_BURNED -> {}
      case SLEEP -> {}
      case HEART_RATE -> {}
    }
    ActivitySource activitySource = activity.getSource();
    if (activitySource == null) {
      activity = activity.withSource(ActivitySource.of(SourceType.MANUAL, null));
    } else {
      if (activitySource.getType() != SourceType.MANUAL) {
        throw new ParamUnexpected("source.type");
      }
      if (activitySource.getProvider() != null) {
        throw new ParamUnexpected("source.provider");
      }
    }
    ActivityRecord savedActivity =
        activityRepository.save(
            activity.toActivityRecord(jsonMapper, SecurityUtil.getOwnerId(principal)));
    return ResponseEntity.ok().body(savedActivity.toActivity(jsonMapper));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Activity> getActivity(
      @PathVariable("id") String activityId, Principal principal)
      throws JsonProcessingException, ParamNotSet {
    Optional<ActivityRecord> activityRecord =
        activityRepository.findByIdAndOwnerIdOrderByCreatedDesc(
            activityId, SecurityUtil.getOwnerId(principal));
    if (activityRecord.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(activityRecord.get().toActivity(jsonMapper));
  }

  @GetMapping(value = {"", "/"})
  public ResponseEntity<List<Activity>> listActivity(Principal principal)
      throws JsonProcessingException, ParamNotSet {
    List<Activity> activityList = new ArrayList<>();
    for (ActivityRecord activityRecord :
        activityRepository.findAllByOwnerIdOrderByCreatedDesc(SecurityUtil.getOwnerId(principal))) {
      activityList.add(activityRecord.toActivity(jsonMapper));
    }
    return ResponseEntity.ok(activityList);
  }

  @PostMapping("/{id}")
  public ResponseEntity<?> updateActivity(
      @PathVariable String id, @RequestBody Activity user, Principal principal)
      throws JsonProcessingException {
    Optional<ActivityRecord> oldUserOptional =
        activityRepository.findByIdAndOwnerIdOrderByCreatedDesc(
            id, SecurityUtil.getOwnerId(principal));
    if (oldUserOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    ActivityRecord oldActivity = oldUserOptional.get();
    if (StringUtils.isNotEmpty(user.getId())
        && !Objects.equals(user.getId(), oldActivity.getId())) {
      return ResponseEntity.badRequest().body(new ParamNotEditable("id"));
    }
    ActivityRecord savedUser =
        activityRepository.save(
            user.toActivityRecord(jsonMapper, SecurityUtil.getOwnerId(principal), oldActivity));
    return ResponseEntity.ok().body(savedUser);
  }
}
