pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.51.0"
////                            # available:"0.60.0"
////                            # available:"0.60.1"
////                            # available:"0.60.2"
    id("com.gradle.enterprise") version "3.12.4"
////                        # available:"3.12.5"
////                        # available:"3.12.6"
////                        # available:"3.13"
////                        # available:"3.13.1"
////                        # available:"3.13.2"
////                        # available:"3.13.3"
////                        # available:"3.13.4"
////                        # available:"3.14"
////                        # available:"3.14.1"
////                        # available:"3.15"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

rootProject.name = "plumbing"

gradle.allprojects {
    group = "io.pixeloutlaw"

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
        maven {
            url = uri("https://repo.minebench.de/")
        }
        maven {
            url = uri("https://repo.codemc.org/repository/nms")
        }
        maven {
            url = uri("https://libraries.minecraft.net")
        }
    }
}

include(
    "plumbing-api",
    "plumbing-1_17_R1",
    "plumbing-1_18_R1",
    "plumbing-1_18_R2",
    "plumbing-1_19_R1",
    "plumbing-1_19_R2",
    "plumbing-1_19_R3",
    "plumbing-1_20_R1",
    "plumbing-1_20_R2",
    "plumbing-lib"
)
