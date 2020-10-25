plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
}

group = "org.openredstone"
version = "2.1"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(project(":fungee-messages"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:1.0.0")
    compileOnly(group = "org.spigotmc", name = "spigot-api", version = "1.16.1-R0.1-SNAPSHOT")
}
