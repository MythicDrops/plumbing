plugins {
    kotlin("jvm") version "1.6.0" apply false
    id("dev.mythicdrops.gradle.project")
}

description = "NMS and OBC Adapters for MythicDrops"

contacts {
    addPerson("topplethenunnery@gmail.com", closureOf<nebula.plugin.contacts.Contact> {
        moniker = "ToppleTheNun"
        github = "ToppleTheNun"
    })
}

mythicDropsRelease {
    repository = "MythicDrops/plumbing"
}
