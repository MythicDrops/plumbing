pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    // See https://jmfayard.github.io/refreshVersions
    id("de.fayard.refreshVersions") version "0.60.5"
    id("com.gradle.develocity") version "3.17.4"
    id("org.gradle.toolchains.foojay-resolver") version "0.8.0"
}

develocity {
    buildScan {
        publishing.onlyIf { it.buildResult.failures.isNotEmpty() && !System.getenv("CI").isNullOrEmpty() }
        termsOfUseUrl = "https://gradle.com/terms-of-service"
        termsOfUseAgree = "yes"
    }
}

toolchainManagement {
    jvm {
        javaRepositories {
            repository("foojay") {
                resolverClass.set(org.gradle.toolchains.foojay.FoojayToolchainResolver::class.java)
            }
        }
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
    "plumbing-1_20_R3",
    "plumbing-lib",
    "plumbing-test-plugin"
)
