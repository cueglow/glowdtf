import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}

group = "org.cueglow"
version = "0.0.1-dev"

repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test-junit5"))
    implementation("io.javalin:javalin:3.11.2")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}