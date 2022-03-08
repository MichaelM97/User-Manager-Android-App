import Dependencies.hilt

plugins {
    id(Plugins.COMMON)
    id(Plugins.HILT)
}

dependencies {
    implementation(project(Modules.NETWORK))
    implementation(project(Modules.Core.MODELS))

    hilt()
    implementation(Dependencies.Kotlin.COROUTINES_CORE)

    testImplementation(project(Modules.Core.TEST))
}
