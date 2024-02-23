import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("dev.mythicdrops.gradle.spigot.build")
}

val minecraftVersion = "1.20.4"
val obcVersion = "v1_20_R3"

description = "NMS and OBC Adapters for $obcVersion for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot:$minecraftVersion-R0.1-SNAPSHOT:remapped-mojang")

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

spigotBuildTools {
    includeRemapped.set(true)
    version.set(minecraftVersion)
}
