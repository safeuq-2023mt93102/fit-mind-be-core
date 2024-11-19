rootProject.name = "fit-mind-be-users"

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      library("jackson-datatype-jsr310", "com.fasterxml.jackson.datatype", "jackson-datatype-jsr310").withoutVersion()
      library("jackson-annotations", "com.fasterxml.jackson.core", "jackson-annotations").withoutVersion()
      library("jackson-databind", "com.fasterxml.jackson.core", "jackson-databind").withoutVersion()
      library("jackson-core", "com.fasterxml.jackson.core", "jackson-core").withoutVersion()

      library("apache-commons-lang3", "org.apache.commons:commons-lang3:3.12.0")
    }
  }
}