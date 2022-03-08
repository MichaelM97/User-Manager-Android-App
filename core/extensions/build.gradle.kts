plugins {
    id(Plugins.COMMON)
}

dependencies {
    implementation(Dependencies.AndroidX.ANNOTATION)

    testImplementation(project(Modules.Core.TEST))
}
