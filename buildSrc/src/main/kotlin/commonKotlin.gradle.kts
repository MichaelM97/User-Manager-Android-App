import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = Versions.JAVA
    targetCompatibility = Versions.JAVA
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = Versions.JAVA.toString()
}

tasks.withType<Test> {
    useJUnitPlatform()
}
