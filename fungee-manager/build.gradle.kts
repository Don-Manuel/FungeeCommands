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
    maven {
        name = "sonatype-oss"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    implementation(project(":plugin-messaging"))
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    compileOnly(group = "net.md-5", name = "bungeecord-api", version = "1.16-R0.4-SNAPSHOT")
}



