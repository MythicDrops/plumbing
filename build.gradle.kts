plugins {
    kotlin("jvm") version "1.9.25" apply false
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
    repository.set("MythicDrops/plumbing")
}
