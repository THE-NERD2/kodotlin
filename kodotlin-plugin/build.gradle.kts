plugins {
    kotlin("jvm") version "2.1.20"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "dev.the_nerd2.kodotlin"
version = "0.1.0-alpha"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":kodotlin-library"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

gradlePlugin {
    plugins {
        create("kodotlin") {
            id = "dev.the_nerd2.kodotlin"
            implementationClass = "dev.the_nerd2.kodotlin.KodotlinPlugin"
        }
    }
}