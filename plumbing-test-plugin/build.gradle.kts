import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("com.github.johnrengelman.shadow")
}

description = "NMS and OBC Adapter Lib for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    implementation(project(":plumbing-lib"))
    implementation("io.pixeloutlaw:kindling:_")
}

kotlin {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
    explicitApi()
}

tasks {
    withType<ProcessResources> {
        filesMatching("plugin.yml") {
            expand("project" to project)
        }
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        dependsOn(":plumbing-lib:shadowJar")

        mergeServiceFiles()
        relocate("kotlin", "io.pixeloutlaw.minecraft.spigot.plumbing.plugin.shade.kotlin")
    }

    named("assemble") {
        dependsOn("shadowJar")
    }

    named("dokkaJavadoc") {
        dependsOn(":plumbing-lib:shadowJar")
    }
}
