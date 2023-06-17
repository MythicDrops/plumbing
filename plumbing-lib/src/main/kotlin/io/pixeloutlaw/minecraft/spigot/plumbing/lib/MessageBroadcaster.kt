package io.pixeloutlaw.minecraft.spigot.plumbing.lib

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractMessageBroadcaster
import io.pixeloutlaw.minecraft.spigot.plumbing.api.MinecraftVersions
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Utility for broadcasting messages about [ItemStack]s.
 */
object MessageBroadcaster {
    private val broadcasterByServer: AbstractMessageBroadcaster by lazy {
        when (MinecraftVersions.nmsVersion) {
            "v1_20_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v120R1.MessageBroadcaster
            "v1_19_R3" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R3.MessageBroadcaster
            "v1_19_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R2.MessageBroadcaster
            "v1_19_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R1.MessageBroadcaster
            "v1_18_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v118R2.MessageBroadcaster
            "v1_18_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v118R1.MessageBroadcaster
            "v1_17_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v117R1.MessageBroadcaster
            else -> {
                NoOpMessageBroadcaster()
            }
        }
    }

    /**
     * Returns `true` if the version of bukkit
     */
    val isSupportedBukkitVersion: Boolean by lazy { MinecraftVersions.isAtLeastMinecraft115 }

    /**
     * Broadcasts a message about the player and item using the given format. Replaces "%player%" with the player's
     * display name and replaces "%item%" with the item tooltip for the given item.
     *
     * @param format Format of message
     * @param player Player to reference
     * @param itemStack ItemStack to reference
     * @param bukkitAudiences Adventure context to use
     * @param target Who should see the broadcast
     */
    fun broadcastItem(
        format: String,
        player: Player,
        itemStack: ItemStack,
        bukkitAudiences: BukkitAudiences,
        target: AbstractMessageBroadcaster.BroadcastTarget,
    ) {
        if (!isSupportedBukkitVersion) return // do nothing for unsupported bukkit versions
        if (broadcasterByServer is NoOpMessageBroadcaster) return // do nothing for NoOp message broadcaster
        broadcasterByServer.broadcastItem(format, player, itemStack, bukkitAudiences, target)
    }

    internal class NoOpMessageBroadcaster : AbstractMessageBroadcaster() {
        override fun convertItemStackToJson(itemStack: ItemStack): String {
            error("Should never be invoked")
        }
    }
}
