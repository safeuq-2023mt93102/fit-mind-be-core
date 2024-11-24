# Fit Mind

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

## Keycloak IAM Platform

### Run Keycloak in docker

The command to run is as follows
```shell
docker run -p 8090:8090 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.4 start-dev --http-port=8090
```

### Export/Import realm data

Export `fit-mind` realm using the following command
```shell
docker exec -it fit-mind-keycloak bash -c 'opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --realm fit-mind --users realm_file'
```

In Docker, the command to export to local system is
```shell
docker cp fit-mind-keycloak:/opt/keycloak/data/import/fit-mind-realm.json keycloak/fit-mind-realm.json
```

Optionally, import `fit-mind` realm using the following command
```shell
opt/keycloak/bin/kc.sh import --file /opt/keycloak/data/import/ fit-mind-realm.json
```

### Client scopes

To map `userId` passed during Keycloak user creation back to the Principal object, we need to add a mapper to the `profile` client scope.
[Reference](https://medium.com/@lakshminp/adding-user-attributes-to-jwt-token-in-keycloak-f3981b7df310)

## Docker

### Building the images

Both `core-be` and `users-be` server images can be built using the following command:

```shell
./gradlew jibDockerBuild
```

In Windows, the following command can be used instead

```shell
gradlew.bat jibDockerBuild
```

To build the frontend server into an image, follow the instructions [here](https://github.com/safeuq-2023mt93102/fit-mind-frontend?tab=readme-ov-file#docker-images)

### Addition setup

Docker containers have their own isolated network. So, the localhost of the guest containers are different 
from the localhost of the host machine. This requires the following changes in images:

- The host of each server in configurations is referred by its container name.
- The Keycloak container requires the `KC_HOSTNAME` property to be specified.
- The realm configuration requires whitelisting the new domain paths (for Web Origins, etc,.).
- The new domain paths must be resolvable to localhost in the host machine.

To fix the domain path resolution, edit the hosts file and include the following entries

```text
127.0.0.1 fit-mind-keycloak
127.0.0.1 fit-mind-fe
```

- In *nix OSes (MacOS, Linux), it is located in `/etc/hosts`. 
- In Windows, it is located in `C:\Windows\System32\Drivers\etc\hosts`

You may need Administrator/Superuser privileges to edit these files.

### Deployment

Once all the images are built, start the containers using Docker compose:

```shell
docker-compose up -d
```

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
    "metadata": {
      "type": "typename",
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