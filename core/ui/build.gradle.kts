import Dependencies.compose

plugins {
    id(Plugins.COMMON_FEATURE)
}

dependencies {
    compose()

    androidTestImplementation(project(Modules.Core.UI_TEST))
}
