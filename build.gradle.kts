plugins {
  java
  id("com.diffplug.spotless") version "6.9.0"
  id("org.springframework.boot") version "3.2.5"
  id("io.spring.dependency-management") version "1.1.4"
}

group = "com.bits.group13"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

spotless {
  format("misc") {
    target("*.md")
    trimTrailingWhitespace()
    endWithNewline()
  }
  java {
    toggleOffOn()
    googleJavaFormat().reflowLongStrings()
  }
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-web")

  compileOnly("org.projectlombok:lombok")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  runtimeOnly("com.h2database:h2")
  annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  implementation(libs.apache.commons.lang3)
  implementation(libs.jackson.databind)
}

tasks.withType<Test> {
  useJUnitPlatform()
}
