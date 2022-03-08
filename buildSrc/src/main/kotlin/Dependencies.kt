import org.gradle.kotlin.dsl.DependencyHandlerScope

object Dependencies {
    object AndroidX {
        const val ANNOTATION = "androidx.annotation:annotation:${Versions.AndroidX.ANNOTATION}"
        const val COMPOSE = "androidx.compose.ui:ui:${Versions.AndroidX.COMPOSE}"
        const val COMPOSE_FOUNDATION = "androidx.compose.foundation:foundation:${Versions.AndroidX.COMPOSE}"
        const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.AndroidX.COMPOSE}"
        const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.AndroidX.COMPOSE}"
        const val COMPOSE_TEST = "androidx.compose.ui:ui-test-junit4:${Versions.AndroidX.COMPOSE}"
        const val COMPOSE_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${Versions.AndroidX.COMPOSE}"
        const val ACTIVITY = "androidx.activity:activity-ktx:${Versions.AndroidX.ACTIVITY}"
        const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:${Versions.AndroidX.ACTIVITY}"
        const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:${Versions.AndroidX.NAVIGATION}"
        const val LIFECYCLE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.LIFECYCLE}"
        const val LIFECYCLE_VIEW_MODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.AndroidX.LIFECYCLE}"
        const val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.AndroidX.HILT_NAVIGATION_COMPOSE}"
        const val ANDROID_TEST_RUNNER = "androidx.test:runner:${Versions.AndroidX.ANDROID_TEST}"
    }

    object Kotlin {
        const val TEST = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.Kotlin.KOTLIN}"
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Kotlin.COROUTINES}"
        const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Kotlin.COROUTINES}"
    }

    object Google {
        const val HILT = "com.google.dagger:hilt-android:${Versions.Google.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.Google.HILT}"
        const val HILT_TEST = "com.google.dagger:hilt-android-testing:${Versions.Google.HILT}"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.Network.RETROFIT}"
        const val OK_HTTP = "com.squareup.okhttp3:okhttp:${Versions.Network.OK_HTTP}"
    }

    object JUnit {
        const val BOM = "org.junit:junit-bom:${Versions.Test.JUNIT5}"
        const val JUPITER = "org.junit.jupiter:junit-jupiter"
        const val JUPITER_API = "org.junit.jupiter:junit-jupiter-api"
        const val JUPITER_PARAMS = "org.junit.jupiter:junit-jupiter-params"
    }

    object Test {
        const val MOCKK = "io.mockk:mockk:${Versions.Test.MOCKK}"
        const val MOCKK_ANDROID = "io.mockk:mockk-android:${Versions.Test.MOCKK}"
    }

    fun DependencyHandlerScope.hilt() {
        "implementation"(Google.HILT)
        "kapt"(Google.HILT_COMPILER)
    }

    fun DependencyHandlerScope.compose() {
        "implementation"(AndroidX.COMPOSE)
        "implementation"(AndroidX.COMPOSE_FOUNDATION)
        "implementation"(AndroidX.COMPOSE_MATERIAL)
        "implementation"(AndroidX.COMPOSE_TOOLING)
        "implementation"(AndroidX.LIFECYCLE_VIEW_MODEL_COMPOSE)
    }

    fun DependencyHandlerScope.commonFeature() {
        "implementation"(AndroidX.ACTIVITY)
        "implementation"(AndroidX.LIFECYCLE_VIEW_MODEL)
    }
}
