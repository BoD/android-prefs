plugins {
  id("java")
  id("com.vanniktech.maven.publish")
}

group = "org.jraf"
version = "1.4.1"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Javadoc> {
  isFailOnError = false
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

dependencies {
  compileOnly(project(":android-sharedpreferences"))
}


// Note: declare your Sonatype OSS credentials and signing GPG key in your home's gradle.properties like this:
// mavenCentralUsername = <user name>
// mavenCentralPassword = <password>
// signing.keyId=<8 character key>
// signing.password=<your password>
// signing.secretKeyRingFile=<absolute path to the gpg private key>
// See also: https://vanniktech.github.io/gradle-maven-publish-plugin/central/#secrets
mavenPublishing {
  publishToMavenCentral(automaticRelease = true)
  signAllPublications()
  pom {
    name.set(project.name)
    description.set("Android preferences for WINNERS!")
    url.set("https://github.com/BoD/android-prefs")
    licenses {
      license {
        name.set("Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0")
        distribution.set("repo")
      }
    }
    developers {
      developer {
        id.set("BoD")
        name.set("Benoit 'BoD' Lubek")
        email.set("BoD@JRAF.org")
        url.set("https://JRAF.org")
        organization.set("JRAF.org")
        organizationUrl.set("https://JRAF.org")
        roles.set(listOf("developer"))
        timezone.set("+1")
      }
    }
    scm {
      connection.set("scm:git:https://github.com/BoD/android-prefs")
      developerConnection.set("scm:git:https://github.com/BoD/android-prefs")
      url.set("https://github.com/BoD/android-prefs")
    }
    issueManagement {
      url.set("https://github.com/BoD/android-prefs/issues")
      system.set("GitHub Issues")
    }
  }
}


// Run `./gradlew publishToMavenLocal` to publish to the local maven repo
// Run `./gradlew publishToMavenCentral` to publish to Maven Central
