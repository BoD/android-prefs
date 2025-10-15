plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  kotlin("kapt")
}

android {
  compileSdkVersion(36)

  defaultConfig {
    applicationId = "org.jraf.android.prefs.sample"
    minSdk = 14
    targetSdk = 36
    versionCode = 1
    versionName = "1.4.1"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  namespace = "org.jraf.android.prefs.sample"
}

kotlin {
  java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

dependencies {
  implementation("androidx.appcompat:appcompat:1.0.0")
  implementation("androidx.annotation:annotation:1.0.0")
  implementation("androidx.lifecycle:lifecycle-livedata:2.0.0")

  kapt(project(":prefs-compiler"))
  implementation(project(":prefs"))
}
