plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
}

description = "NMS and OBC Adapter API for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    api("net.kyori:adventure-platform-bukkit:_")

    implementation(kotlin("stdlib"))
    implementation("net.kyori:adventure-text-serializer-gson:_") {
        exclude(group = "com.google.code.gson", module = "gson")
    }
}
