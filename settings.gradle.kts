pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.23.0"
}

rootProject.name = "plumbing"

gradle.allprojects {
    group = "io.pixeloutlaw"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.minebench.de/")
        }
        maven {
            url = uri("https://repo.codemc.org/repository/nms")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
}

include(
    "plumbing-api",
    "plumbing-1_17_R1",
    "plumbing-1_18_R1",
    "plumbing-lib"
)
