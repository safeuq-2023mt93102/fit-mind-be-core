import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
  java

  id("io.freefair.lombok") version "8.11"
  id("com.diffplug.spotless") version "6.25.0"
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
}

allprojects {
  apply(plugin = "java")
  apply(plugin = "com.diffplug.spotless")

  group = "com.bits.ss.fitmind"
  version = "0.0.1-SNAPSHOT"

  repositories {
    mavenLocal()
    mavenCentral()
  }

  java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
  }

  configure<SpotlessExtension> {
    format("misc") {
      target("*.md")
      trimTrailingWhitespace()
      endWithNewline()
    }
    java {
      toggleOffOn()
      googleJavaFormat().reflowLongStrings()
    }
    kotlinGradle { ktfmt().googleStyle() }
  }
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

tasks.withType<Test> { useJUnitPlatform() }
