import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.karumi.kotlinsnapshot:plugin:2.2.2")
    }
}

apply(plugin = "com.karumi.kotlin-snapshot")

plugins {
    kotlin("jvm") version "1.4.10"
    application
    id("com.github.node-gradle.node") version "2.2.4"
    id("com.dorongold.task-tree") version "1.5"
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
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.github.kittinunf.fuel:fuel:2.3.1")
    testImplementation("org.java-websocket:Java-WebSocket:1.5.1")
    testImplementation("org.awaitility:awaitility-kotlin:4.0.3")
    testImplementation("com.google.truth:truth:1.1.2")

    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.9")

    implementation("io.javalin:javalin:3.11.2")

    implementation("org.apache.logging.log4j:log4j-api:2.13.3")
    implementation("org.apache.logging.log4j:log4j-core:2.13.3")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")

    implementation("com.beust:klaxon:5.5")

    implementation("org.jgrapht:jgrapht-core:1.5.1")
    implementation("org.jgrapht:jgrapht-io:1.5.1")

    implementation("de.deltaeight:LibArtNet:1.1.2-beta")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.13.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

// JAXB
// in Groovy for copy-pasting from StackOverflow
apply(from="jaxb.gradle")

apply(from="npm.gradle")

