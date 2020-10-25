import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
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
    testImplementation(kotlin("test-junit5"))

    implementation("io.javalin:javalin:3.11.2")

    implementation("org.apache.logging.log4j:log4j-api:2.13.3")
    implementation("org.apache.logging.log4j:log4j-core:2.13.3")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.13.3")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}

// Copy the static files from webui into the jar
//processResources {
//    from ('frontend/dist/') {
//        into 'public'
//    }
//}