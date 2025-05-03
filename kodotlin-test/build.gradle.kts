plugins {
    kotlin("jvm") version "2.1.20"
    id("dev.the_nerd2.kodotlin")
}

group = "dev.the_nerd2.kodotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}