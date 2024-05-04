# Fitness Tracker

## Features:
* **User Authentication:** Implement a secure user authentication system to allow users to register, log in,
  and manage their accounts.
* **Activity Logging:** Enable users to log various fitness activities, including workouts, runs, walks, cycling
  sessions, and more. Users should be able to record duration, distance, intensity, and calories burned.
* **Goal Setting:** Provide users with the ability to set personalized fitness goals based on parameters like
  weight loss, endurance improvement, muscle gain, or specific performance targets.
* **Progress Tracking:** Display visual representations of users' progress toward their fitness goals. Allow users
  to track their improvements over time and celebrate milestones.
* **Workout Plans:** Offer pre-designed workout plans or allow users to create custom workout routines 
  tailored to their preferences and fitness levels.
* **Integration with Wearable Devices:** Integrate with popular fitness trackers and smartwatches to 
  automatically sync activity data and provide a seamless user experience. (** Mock API s can be used)
* **Nutrition Tracking:** Optionally, incorporate features for tracking nutrition intake, including calorie
  counting, macronutrient monitoring, and meal logging.
* **Social Sharing:** Enable users to share their fitness achievements, goals, and progress on social media
  platforms to encourage accountability and motivate others.

## API Shape:
### User Authentication:
- `POST /users` - Create a user
- `POST /users/{id}` - Update user details
- `GET /users/{id}` - Retrieve a user

### Activity Logging:
- `POST /activity` - Create an activity
  ```json
  {
    "id": "value",
    "type": "typename",
    "value": 0,
    "metadata": {
      "key": "value"
    },
    "source": {
      "type": "integration/manual",
      "provider": null 
    }
  }
  ```
- `GET /activity/{id}` - Retrieve an activity
- `GET /activity` - List all activity. Paginated & supports filters
- `POST /activity/{id}` - Update an activity

### Goal Setting:
- `POST /goals` - Create a goal
  ```json
  {
    "id": "value",
    "type": "typename"
  }
  ```
- `GET /goals/{id}` - Retrieve a goal
- `GET /goals` - List all goals. Paginated & supports filters
- `POST /goals/{id}` - Update a goal

### Progress Tracking:
- Frontend requirement
- Show time series activity per goal
  - `GET /goals/{id}/trend` - Aggregate API. Specify filters & config

### Workout Plans:
- `POST /plans` - Create a plan
- `GET /plans/{id}` - Retrieve a plan
- `GET /plans` - List all plans. Paginated & supports filters
- `POST /plans/{id}` - Update a plan

### Integration with Wearable Devices:
Integration usually is as follows:
- Backend calls the third-party service and receives a unique token
- Backend passes this to frontend to show the integration dialog
- Third-party service sends a success response:
  - Backend receives this as a webhook
  - Frontend receives a redirection response
- Backend updates the integration status

APIs for integration:
- `POST /integrations` - Start a new integration. Metadata is integration specific
  ```json
  {
    "id": "value",
    "type": "typename",
    "metadata": {
      "key": "value"
    }
  }
  ```
- `GET /integrations/{id}` - Retrieve an integration
- `GET /integrations` - List all integrations. Paginated & supports filters
- `POST /integrations/{id}` - Update an integration
- `DELETE /integrations/{id}` - Delete an existing integration

### Nutrition Tracking:
- `POST /nutrition` - Log a nutrition intake
- `GET /nutrition/{id}` - Retrieve a nutrition intake
- `GET /nutrition` - List all nutrition intakes. Paginated & supports filters
- `POST /nutrition/{id}` - Update a nutrition intake

### Social Sharing:
- A basic idea is to click a button in frontend and we:
  - Generate shareable image and text for social media
  - Redirect to social media with some form of API that pre-populates the content

Twitter reference:
- [Tweet button](https://developer.twitter.com/en/docs/twitter-for-websites/tweet-button/overview)
- [Summary cards](https://developer.twitter.com/en/docs/twitter-for-websites/cards/guides/getting-started#started)