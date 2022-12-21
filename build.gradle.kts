plugins {
    kotlin("jvm") version "1.7.20" apply false
    id("dev.mythicdrops.gradle.project")
    id("io.pixeloutlaw.spigot.build")
}

description = "NMS and OBC Adapters for MythicDrops"

contacts {
    addPerson("topplethenunnery@gmail.com", closureOf<nebula.plugin.contacts.Contact> {
        moniker = "ToppleTheNun"
        github = "ToppleTheNun"
    })
}

mythicDropsRelease {
    repository.set("MythicDrops/plumbing")
}

spigotBuildTools {
    skipExistingVersions = true
    versions = listOf("1.17.1", "1.18.1", "1.18.2", "1.19.2", "1.19.3")
}
