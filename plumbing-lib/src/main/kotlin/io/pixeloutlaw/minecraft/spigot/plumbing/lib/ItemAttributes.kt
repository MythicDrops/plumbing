package io.pixeloutlaw.minecraft.spigot.plumbing.lib

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractItemAttributes
import io.pixeloutlaw.minecraft.spigot.plumbing.api.MinecraftVersions
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

public object ItemAttributes {
    private val itemAttributesByServer: AbstractItemAttributes by lazy {
        when (MinecraftVersions.nmsVersion) {
            "v1_20_R3" -> io.pixeloutlaw.minecraft.spigot.plumbing.v120R3.ItemAttributes
            "v1_20_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v120R2.ItemAttributes
            "v1_20_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v120R1.ItemAttributes
            "v1_19_R3" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R3.ItemAttributes
            "v1_19_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R2.ItemAttributes
            "v1_19_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R1.ItemAttributes
            "v1_18_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v118R2.ItemAttributes
            "v1_18_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v118R1.ItemAttributes
            "v1_17_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v117R1.ItemAttributes
            else -> NoOpItemAttributes
        }
    }

    /**
     * Returns `true` if the version of bukkit
     */
    public val isSupportedBukkitVersion: Boolean by lazy { MinecraftVersions.isAtLeastMinecraft117 }

    /**
     * Creates a copy of the ItemStack with default attributes if it doesn't already have
     * attributes attached.
     */
    public fun conditionallyCloneWithDefaultAttributes(itemStack: ItemStack): ItemStack {
        if (itemStack.itemMeta?.hasAttributeModifiers() == true) {
            return itemStack.clone()
        }
        return cloneWithDefaultAttributes(itemStack)
    }

    /**
     * Creates a copy of the ItemStack with default attributes.
     */
    public fun cloneWithDefaultAttributes(itemStack: ItemStack): ItemStack {
        if (!isSupportedBukkitVersion) {
            return itemStack.clone()
        }
        return itemAttributesByServer.cloneWithDefaultAttributes(itemStack)
    }

    internal object NoOpItemAttributes : AbstractItemAttributes() {
        override val availableAttributes: List<Attribute> = emptyList()

        override val availableEquipmentSlots: List<EquipmentSlot> = emptyList()

        override fun handleEquipmentSlot(
            itemStack: ItemStack,
            slot: EquipmentSlot,
            itemMeta: ItemMeta,
        ) {
            // do nothing
        }
    }
}
