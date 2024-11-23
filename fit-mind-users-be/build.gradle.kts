plugins {
  java

  id("org.springframework.boot")
  id("io.spring.dependency-management")
  id("com.google.cloud.tools.jib")
}

dependencies {
  implementation(project(":fit-mind-commons"))
  implementation(project(":fit-mind-commons-api"))
  implementation(project(":fit-mind-users-api"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

  implementation(libs.apache.commons.lang3)
  implementation(libs.jackson.databind)
  implementation(libs.jakarta.ws.rs.api)
  implementation(libs.keycloak.admin.client)
  runtimeOnly("com.h2database:h2")

  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

jib {
  container {
    mainClass = "com.bits.ss.fitmind.FitMindBeUsersApplication"
  }
  from {
    image = "eclipse-temurin:17.0.13_11-jre"
    platforms {
      platform {
        architecture = "arm64"
        os = "linux"
      }
    }
  }
  to {
    image = "fitmind/users-be:${project.version}"
  }
  containerizingMode = "packaged"
}