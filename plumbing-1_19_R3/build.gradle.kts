plugins {
    kotlin("jvm")
}

description = "NMS and OBC Adapters for 1.19R3 for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot:1.19.4-R0.1-SNAPSHOT")

    api(project(":plumbing-api"))

    implementation(kotlin("stdlib"))
    implementation("io.pixeloutlaw:kindling:_")
}
