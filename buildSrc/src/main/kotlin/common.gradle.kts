plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Versions.App.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.App.MIN_SDK
        targetSdk = Versions.App.TARGET_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }

    kotlinOptions.jvmTarget = Versions.JAVA.toString()

    packagingOptions.resources.excludes.addAll(Configuration.excludedPackagingOptions)

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

kapt.correctErrorTypes = true
