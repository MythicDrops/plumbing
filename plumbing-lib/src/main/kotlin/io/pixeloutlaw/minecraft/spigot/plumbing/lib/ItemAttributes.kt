package io.pixeloutlaw.minecraft.spigot.plumbing.lib

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractItemAttributes
import io.pixeloutlaw.minecraft.spigot.plumbing.api.MinecraftVersions
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object ItemAttributes {
    private val availableEquipmentSlots by lazy {
        EquipmentSlot.values()
    }
    private val itemAttributesByServer: AbstractItemAttributes by lazy {
        when (MinecraftVersions.nmsVersion) {
            "v1_18_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v118R1.ItemAttributes
            "v1_17_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v117R1.ItemAttributes
            else -> NoOpItemAttributes
        }
    }

    /**
     * Returns `true` if the version of bukkit
     */
    val isSupportedBukkitVersion: Boolean by lazy { MinecraftVersions.isAtLeastMinecraft116 }

    fun cloneWithDefaultAttributes(itemStack: ItemStack): ItemStack {
        if (!isSupportedBukkitVersion) {
            return itemStack
        }
        return itemAttributesByServer.cloneWithDefaultAttributes(itemStack)
    }

    internal object NoOpItemAttributes : AbstractItemAttributes {
        override fun cloneWithDefaultAttributes(itemStack: ItemStack): ItemStack {
            return itemStack
        }
    }
}
