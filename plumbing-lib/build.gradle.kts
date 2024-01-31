import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
}

description = "NMS and OBC Adapter Lib for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    api(project(":plumbing-api"))

    implementation(kotlin("stdlib"))
    rootProject.subprojects.filter { it.name.contains("_R") }.forEach {
        implementation(project(":${it.name}"))
    }
}

kotlin {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
    explicitApi()
}
