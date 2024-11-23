plugins {
  java
  `maven-publish`

  id("io.freefair.lombok") version "8.11"
}

dependencies {
  implementation(libs.keycloak.admin.client)
  implementation(libs.apache.commons.lang3)
  implementation(libs.jackson.databind)
}

tasks.withType<Test> { useJUnitPlatform() }

publishing { publications { create<MavenPublication>("main") { from(components["java"]) } } }
