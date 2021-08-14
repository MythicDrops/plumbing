plugins {
    kotlin("jvm")
}

description = "NMS and OBC Adapters for 1.17R1 for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot:1.17-R0.1-SNAPSHOT")

    api(project(":plumbing-api"))

    implementation(kotlin("stdlib-jdk8"))
}
