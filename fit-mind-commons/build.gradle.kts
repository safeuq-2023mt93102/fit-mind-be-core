plugins {
  java
  `maven-publish`

  id("io.freefair.lombok") version "8.11"
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
}

dependencies {
  implementation(project(":fit-mind-commons-api"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-security")

  // OAuth
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

  implementation(libs.keycloak.admin.client)
  implementation(libs.apache.commons.lang3)
  implementation(libs.jackson.databind)
  implementation(libs.jackson.core)
}

tasks.withType<Test> { useJUnitPlatform() }

publishing { publications { create<MavenPublication>("main") { from(components["java"]) } } }
