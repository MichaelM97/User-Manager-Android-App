plugins {
    id(Plugins.COMMON)
}

dependencies {
    api(platform(Dependencies.JUnit.BOM))
    api(Dependencies.JUnit.JUPITER)
    api(Dependencies.JUnit.JUPITER_API)
    api(Dependencies.JUnit.JUPITER_PARAMS)
    api(Dependencies.Kotlin.TEST)
    api(Dependencies.Kotlin.COROUTINES_TEST)
    api(Dependencies.Test.MOCKK)
}
