import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
  id("com.diffplug.spotless") version "6.25.0"
  id("io.freefair.lombok") version "8.11" apply false
  id("org.springframework.boot") version "3.2.5" apply false
  id("com.google.cloud.tools.jib") version "3.4.3" apply false
  id("io.spring.dependency-management") version "1.1.4" apply false
}

allprojects {
  group = "com.bits.ss.fitmind"
  version = "0.0.1-SNAPSHOT"

  repositories {
    mavenLocal()
    mavenCentral()
  }
}

subprojects {
  apply(plugin = "java")
  apply(plugin = "io.freefair.lombok")
  apply(plugin = "com.diffplug.spotless")

  configure<JavaPluginExtension> {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
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
    kotlinGradle { ktfmt().googleStyle() }
  }

  tasks.withType<Test> { useJUnitPlatform() }
}

spotless {
  kotlinGradle { ktfmt().googleStyle() }
}