plugins {
    id("common")
}

android {
    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Versions.AndroidX.COMPOSE
}
