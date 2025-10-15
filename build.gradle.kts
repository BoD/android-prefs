plugins {
  id("com.github.ben-manes.versions").version("0.53.0")
  id("com.android.application").version("8.13.0").apply(false)
  id("com.android.library").version("8.13.0").apply(false)
  id("org.jetbrains.kotlin.android").version("2.2.20").apply(false)
  kotlin("kapt").version("2.2.20").apply(false)
}

// Run './gradlew dependencyUpdates' to see new versions of dependencies
