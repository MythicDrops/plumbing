package io.pixeloutlaw.minecraft.spigot.plumbing.lib

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractItemAttributes
import io.pixeloutlaw.minecraft.spigot.plumbing.api.MinecraftVersions
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
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
            else -> {
                NoOpItemAttributes()
            }
        }
    }

    /**
     * Returns `true` if the version of bukkit
     */
    val isSupportedBukkitVersion: Boolean by lazy { MinecraftVersions.isAtLeastMinecraft116 }

    fun getDefaultItemAttributes(itemStack: ItemStack): Multimap<Attribute, AttributeModifier> {
        val results = HashMultimap.create<Attribute, AttributeModifier>()
        availableEquipmentSlots.forEach { slot ->
            results.putAll(getDefaultItemAttributes(itemStack, slot))
        }
        return results
    }

    fun getDefaultItemAttributes(
        itemStack: ItemStack,
        equipmentSlot: EquipmentSlot
    ): Multimap<Attribute, AttributeModifier> {
        if (!isSupportedBukkitVersion) return HashMultimap.create()
        return itemAttributesByServer.getDefaultItemAttributes(itemStack, equipmentSlot)
    }

    internal class NoOpItemAttributes : AbstractItemAttributes {
        override fun getDefaultItemAttributes(
            itemStack: ItemStack,
            equipmentSlot: EquipmentSlot
        ): Multimap<Attribute, AttributeModifier> {
            return HashMultimap.create()
        }
    }
}
