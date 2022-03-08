pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "User Manager"
include(":app")
include(":core:ui")
include(":core:models")
include(":core:extensions")
include(":core:test")
include(":core:uitest")
include(":data")
include(":network")
include(":userlist:feature")
include(":userlist:domain")
