plugins {
    kotlin("jvm")
}

description = "NMS and OBC Adapters for 1.20R1 for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")

    api(project(":plumbing-api"))

    implementation(kotlin("stdlib"))
    implementation("io.pixeloutlaw:kindling:_")
}
