plugins {
  id("java")
  id("maven-publish")
}

group = "org.jraf"
version = "1.4.0"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Javadoc> {
  isFailOnError = false
//    options.links("http://d.android.com/reference")
}

val sourcesJar = tasks.register("sourcesJar", Jar::class) {
  dependsOn("classes")
  archiveClassifier.set("sources")
  from(sourceSets.main.get().allSource)
}

val javadocJar = tasks.register("javadocJar", Jar::class) {
  dependsOn("javadoc")
  archiveClassifier.set("javadoc")
  from(tasks.named<Javadoc>("javadoc").get().destinationDir)
}

artifacts {
  add("archives", sourcesJar)
  add("archives", javadocJar)
}

dependencies {
  compileOnly(project(":android-sharedpreferences"))
}

// Use "./gradlew install" to deploy the artifacts to your local maven repository