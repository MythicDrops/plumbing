package io.pixeloutlaw.minecraft.spigot.plumbing.api

import org.bukkit.Bukkit

/**
 * Utility for determining which version of Minecraft is in use.
 */
public object MinecraftVersions {
    private const val THIRD_DOT = 3

    public val nmsVersion: String by lazy {
        Bukkit.getServer().javaClass.`package`.name.split(".")[THIRD_DOT]
    }

    public val isAtLeastMinecraft120: Boolean by lazy {
        try {
            Class.forName("org.bukkit.block.data.Hatchable")
            true
        } catch (ex: ClassNotFoundException) {
            false
        }
    }

    public val isAtLeastMinecraft119: Boolean by lazy {
        try {
            Class.forName("org.bukkit.block.SculkCatalyst")
            true
        } catch (ex: ClassNotFoundException) {
            false
        }
    }

    /**
     * Returns true if the Axolotl interface exists, which means we're in 1.17+.
     */
    public val isAtLeastMinecraft117: Boolean by lazy {
        try {
            Class.forName("org.bukkit.entity.Axolotl")
            true
        } catch (ex: ClassNotFoundException) {
            false
        }
    }
}
