plugins {
    id(Plugins.COMMON)
}

dependencies {
    api(Dependencies.AndroidX.COMPOSE_TEST)
    api(Dependencies.AndroidX.COMPOSE_TEST_MANIFEST)
    api(Dependencies.Kotlin.TEST)
    api(Dependencies.Test.MOCKK_ANDROID)
}
