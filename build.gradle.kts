allprojects {
    configurations.all {
        resolutionStrategy.force("org.objenesis:objenesis:2.6")
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
