import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.karumi.kotlinsnapshot:plugin:2.2.3")
    }
}

apply(plugin = "com.karumi.kotlin-snapshot")

plugins {
    kotlin("jvm") version "1.6.10"
    application
    id("com.github.node-gradle.node") version "3.1.1"
    id("com.dorongold.task-tree") version "2.1.0"
    id("com.github.ben-manes.versions") version "0.39.0"
}

group = "org.cueglow"
version = "0.0.1-dev"

application {
    mainClass.set("org.cueglow.server.GlowDtfServerKt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.8.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.github.kittinunf.fuel:fuel:2.3.1")
    testImplementation("org.java-websocket:Java-WebSocket:1.5.2")
    testImplementation("org.awaitility:awaitility-kotlin:4.1.1")
    testImplementation("com.google.truth:truth:1.1.3")

    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.13")

    implementation("io.javalin:javalin:4.1.1")

    implementation("org.apache.logging.log4j:log4j-api:2.17.0")
    implementation("org.apache.logging.log4j:log4j-core:2.17.0")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.1.0")

    implementation("com.beust:klaxon:5.5")

    implementation("org.jgrapht:jgrapht-core:1.5.1")
    implementation("org.jgrapht:jgrapht-io:1.5.1")

    implementation("de.deltaeight:LibArtNet:1.1.2-beta")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.17.0")
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

