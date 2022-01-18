plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.github.ben-manes.versions") version "0.41.0"

    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    // spring-boot dependencies
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.2")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.6.2")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.2")

    // --- dependencies ---

    implementation("com.github.jactor-rises:jactor-shared:0.3.4")
    implementation("org.webjars:bootstrap:5.1.3")
    implementation("org.webjars:jquery:3.6.0")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")

    // test
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.10")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.2")
}

group = "com.github.jactor-rises"
version = "1.0.x-SNAPSHOT"
description = "jactor-web"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io") // resolve maven package repository for github
}

tasks.compileKotlin {
    kotlinOptions {
        allWarningsAsErrors = true
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
}