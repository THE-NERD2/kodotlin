plugins {
    kotlin("jvm")
}

group = "dev.the_nerd2.kodotlin"
version = "0.1.0-alpha"

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