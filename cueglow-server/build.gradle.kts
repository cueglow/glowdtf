import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
    id("com.github.node-gradle.node") version "2.2.4"
}

group = "org.cueglow"
version = "0.0.1-dev"

application {
    mainClass.set("org.cueglow.server.CueGlowServerKt")
}

repositories {
    mavenCentral()
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    implementation("io.javalin:javalin:3.11.2")

    implementation("org.apache.logging.log4j:log4j-api:2.13.3")
    implementation("org.apache.logging.log4j:log4j-core:2.13.3")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")

    implementation("com.beust:klaxon:5.4")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.13.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}


apply(from="npm.gradle")

