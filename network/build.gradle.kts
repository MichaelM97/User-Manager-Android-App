import Dependencies.hilt
import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id(Plugins.COMMON)
    id(Plugins.HILT)
}

android {
    defaultConfig {
        val keys = loadProperties(rootProject.relativePath("keys.properties"))
        buildConfigField("String", "GO_REST_KEY", keys.getProperty("GO_REST_KEY"))
    }
}

dependencies {
    hilt()
    api(Dependencies.Network.RETROFIT)
    api(Dependencies.Network.RETROFIT_GSON_CONVERTER)
    implementation(Dependencies.Network.OK_HTTP)

    testImplementation(project(Modules.Core.TEST))
}
