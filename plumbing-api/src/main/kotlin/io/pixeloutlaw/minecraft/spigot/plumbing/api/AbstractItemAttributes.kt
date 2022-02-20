package io.pixeloutlaw.minecraft.spigot.plumbing.api

import org.bukkit.attribute.Attribute
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Utility for getting default attributes from items.
 */
abstract class AbstractItemAttributes {
    abstract val availableAttributes: List<Attribute>

    abstract val availableEquipmentSlots: List<EquipmentSlot>

    /**
     * Creates a copy of the ItemStack with default attributes.
     */
    fun cloneWithDefaultAttributes(itemStack: ItemStack): ItemStack {
        val originalItemMeta = itemStack.itemMeta ?: return itemStack.clone()
        val itemMeta = originalItemMeta.clone()
        availableEquipmentSlots.forEach { slot ->
            handleEquipmentSlot(itemStack, slot, itemMeta)
        }
        val cloned = itemStack.clone()
        cloned.itemMeta = itemMeta
        return cloned
    }

    abstract fun handleEquipmentSlot(
        itemStack: ItemStack,
        slot: EquipmentSlot,
        itemMeta: ItemMeta
    )

    protected fun attributeFromString(name: String): Attribute? = availableAttributes.find { name.endsWith(it.key.key) }
}
