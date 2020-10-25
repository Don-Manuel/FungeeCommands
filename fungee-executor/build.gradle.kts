plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "2.0.4"
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
    implementation(project(":plugin-messaging"))
    compileOnly(group = "org.spigotmc", name = "spigot-api", version = "1.16.1-R0.1-SNAPSHOT")
}



