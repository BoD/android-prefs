pluginManagement {
  repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    mavenCentral()
    google()
    mavenLocal()
  }
}

include(
  ":sample",
  ":prefs-compiler",
  ":prefs",
  ":android-sharedpreferences",
)