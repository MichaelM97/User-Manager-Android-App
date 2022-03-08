import org.gradle.api.JavaVersion

object Versions {
    object App {
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0.0"
        const val MIN_SDK = 21
        const val TARGET_SDK = 31
        const val COMPILE_SDK = 31
    }

    object AndroidX {
        const val ANNOTATION = "1.3.0"
        const val COMPOSE = "1.1.1"
        const val ACTIVITY = "1.4.0"
        const val NAVIGATION = "2.4.1"
        const val LIFECYCLE = "2.4.0"
        const val HILT_NAVIGATION_COMPOSE = "1.0.0"
        const val ANDROID_TEST = "1.4.0"
    }

    object Kotlin {
        // Make sure to update `buildSrc/build.gradle.kts` when updating the `KOTLIN` version
        const val KOTLIN = "1.6.10"
        const val COROUTINES = "1.6.0"
    }

    object Google {
        // Make sure to update `buildSrc/build.gradle.kts` when updating the `HILT` version
        const val HILT = "2.38.1"
    }

    object Network {
        const val RETROFIT = "2.9.0"
        const val OK_HTTP = "4.9.3"
    }

    object Test {
        const val JUNIT5 = "5.7.1"
        const val MOCKK = "1.12.3"
    }

    val JAVA = JavaVersion.VERSION_11
}
