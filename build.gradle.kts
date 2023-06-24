import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    id("com.google.cloud.tools.jib") version "3.3.2"
}

group = "o.sur.common"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    // Telegram Bots API for creating bots in Telegram
    implementation("org.telegram:telegrambots:6.7.0")

    // Essential Spring Boot Starters for Building Spring Applications Quickly
    implementation("org.springframework.boot:spring-boot-starter")

    // Spring Boot JDBC starter for accessing relational databases
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.1.1")

    // Spring Data JPA starter to work with relational data with JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.1")

    // Kotlin Reflection Library, used to work with Kotlin metadata at runtime
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Jackson Module for Kotlin to serialize and deserialize Kotlin objects to JSON and vice versa
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // kotlin-logging library for logging in Kotlin
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

    // OkHttp - HTTP client for sending HTTP requests and receiving responses
    implementation("com.squareup.okhttp3:okhttp:4.11.0")

    // Emoji-java to work with emoji in Java
    implementation("com.vdurmont:emoji-java:5.1.1")

    // PostgreSQL driver for connecting to a PostgreSQL database
    implementation("org.postgresql:postgresql:42.6.0")

    // Liquibase for database schema versioning and migrations
    implementation("org.liquibase:liquibase-core:4.22.0")

    // Dependencies for Testing Spring Boot Applications
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> { useJUnitPlatform() }

jib {

    to {
        image = "joleksiysurovtsev/${project.name}"
        tags = mutableSetOf(version.toString())
    }
    container {
        jvmFlags = listOf("-Xms512m", "-Xmx512m")
        mainClass = "o.sur.app.moviesbot.MoviesBotApplication"
        ports = listOf("8080")
    }
}
