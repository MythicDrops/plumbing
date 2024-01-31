import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
}

description = "NMS and OBC Adapter API for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    api("net.kyori:adventure-platform-bukkit:_")

    implementation(kotlin("stdlib"))
    implementation("io.pixeloutlaw:kindling:_")
    implementation("net.kyori:adventure-text-serializer-gson:_") {
        exclude(group = "com.google.code.gson", module = "gson")
    }
}

kotlin {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
    }
    explicitApi()
}
