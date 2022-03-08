import Dependencies.commonFeature
import Dependencies.compose
import Dependencies.hilt

plugins {
    id(Plugins.COMMON_FEATURE)
    id(Plugins.HILT)
}

dependencies {
    implementation(project(Modules.UserList.DOMAIN))
    implementation(project(Modules.Core.UI))
    implementation(project(Modules.Core.MODELS))
    implementation(project(Modules.Core.EXTENSIONS))

    hilt()
    compose()
    commonFeature()
    implementation(Dependencies.AndroidX.NAVIGATION_COMPOSE)
    implementation(Dependencies.AndroidX.HILT_NAVIGATION_COMPOSE)

    testImplementation(project(Modules.Core.TEST))

    androidTestImplementation(project(Modules.Core.UI_TEST))
    kaptAndroidTest(Dependencies.Google.HILT_COMPILER)
}
