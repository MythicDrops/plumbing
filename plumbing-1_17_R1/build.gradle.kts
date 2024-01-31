import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
}

description = "NMS and OBC Adapters for 1.17R1 for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot:1.17.1-R0.1-SNAPSHOT")

    api(project(":plumbing-api"))

    implementation(kotlin("stdlib"))
    implementation("io.pixeloutlaw:kindling:_")
}

kotlin {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
    explicitApi()
}
