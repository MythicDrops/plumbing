plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.16.2-R0.1-SNAPSHOT")

    api(project(":plumbing-api"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:_")
}
