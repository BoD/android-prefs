plugins {
  id("java")
  id("maven-publish")
}

group = "org.jraf"
version = "1.0.0"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Javadoc> {
  isFailOnError = false
}

dependencies {
}