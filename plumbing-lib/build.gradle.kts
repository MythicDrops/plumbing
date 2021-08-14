plugins {
    kotlin("jvm")
}

description = "NMS and OBC Adapter Lib for MythicDrops"

dependencies {
    compileOnly("org.spigotmc:spigot-api:_")

    api(project(":plumbing-api"))

    implementation(kotlin("stdlib-jdk8"))
    rootProject.subprojects.filter { it.name.contains("_R") }.forEach {
        implementation(project(":${it.name}"))
    }
}
