package io.pixeloutlaw.minecraft.spigot.plumbing.api

import org.bukkit.inventory.ItemStack

/**
 * Utility for getting default attributes from items.
 */
interface AbstractItemAttributes {
    /**
     * Creates a copy of the ItemStack with default attributes.
     */
    fun cloneWithDefaultAttributes(itemStack: ItemStack): ItemStack
}
