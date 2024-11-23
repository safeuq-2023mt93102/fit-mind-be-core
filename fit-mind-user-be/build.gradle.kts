plugins {
  java

  id("io.freefair.lombok") version "8.11"
  id("com.diffplug.spotless") version "6.25.0"
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
}

dependencies {
  implementation(project(":fit-mind-commons"))
  implementation(project(":fit-mind-commons-api"))
  implementation(project(":fit-mind-user-api"))

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