plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "org.openredstone"
version = "2.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0")
}
