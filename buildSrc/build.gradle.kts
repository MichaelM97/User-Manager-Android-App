plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.0")

    // This should match the Kotlin version exposed by `Versions.kt`
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

    // This should match the Hilt version exposed by `Versions.kt`
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.38.1")

    implementation(kotlin("script-runtime"))
}
