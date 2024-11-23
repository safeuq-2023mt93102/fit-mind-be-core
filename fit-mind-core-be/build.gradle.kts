import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
  java

  id("org.springframework.boot")
  id("io.spring.dependency-management")
  id("com.google.cloud.tools.jib")
}

dependencies {
  implementation(project(":fit-mind-commons"))
  implementation(project(":fit-mind-commons-api"))

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  implementation("com.bits.ss.fitmind:fit-mind-user-api:0.0.1-SNAPSHOT")
  implementation(libs.vavr)
  implementation(libs.janino)
  implementation(libs.jackson.core)
  implementation(libs.jackson.databind)
  implementation(libs.apache.commons.lang3)
  implementation(libs.jakarta.ws.rs.api)
  implementation(libs.keycloak.admin.client)
  runtimeOnly("com.h2database:h2")
}

jib {
  container {
    mainClass = "com.bits.ss.fitmind.FitMindBeCoreApplication"
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
    image = "fitmind/core-be:${project.version}"
  }
  containerizingMode = "packaged"
}
