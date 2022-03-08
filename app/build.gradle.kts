import Dependencies.compose
import Dependencies.hilt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Versions.App.COMPILE_SDK

    defaultConfig {
        applicationId = "com.michaelmccormick.usermanager"
        minSdk = Versions.App.MIN_SDK
        targetSdk = Versions.App.TARGET_SDK
        versionCode = Versions.App.VERSION_CODE
        versionName = Versions.App.VERSION_NAME
        testInstrumentationRunner = "com.michaelmccormick.usermanager.HiltTestRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = Versions.JAVA
        targetCompatibility = Versions.JAVA
    }

    kotlinOptions.jvmTarget = Versions.JAVA.toString()

    packagingOptions.resources.excludes.addAll(Configuration.excludedPackagingOptions)

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = Versions.AndroidX.COMPOSE
}

kapt.correctErrorTypes = true

dependencies {
    implementation(project(Modules.DATA))
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.Core.MODELS))
    implementation(project(Modules.UserList.FEATURE))
    implementation(project(Modules.UserList.DOMAIN))

    hilt()
    compose()
    implementation(Dependencies.AndroidX.ACTIVITY_COMPOSE)
    implementation(Dependencies.AndroidX.NAVIGATION_COMPOSE)

    androidTestImplementation(project(Modules.Core.UI_TEST))
    androidTestImplementation(Dependencies.AndroidX.ANDROID_TEST_RUNNER)
    androidTestImplementation(Dependencies.Google.HILT_TEST)
    kaptAndroidTest(Dependencies.Google.HILT_COMPILER)
}
