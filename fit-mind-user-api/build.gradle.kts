plugins {
  java
}

repositories {
  mavenLocal()
  mavenCentral()
}

dependencies {
  implementation(project(":fit-mind-commons-api"))

  implementation(libs.jackson.annotations)
  implementation(libs.apache.commons.lang3)
}