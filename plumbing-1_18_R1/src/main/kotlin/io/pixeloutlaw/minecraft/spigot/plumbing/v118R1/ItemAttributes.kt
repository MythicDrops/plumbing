package io.pixeloutlaw.minecraft.spigot.plumbing.v118R1

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractItemAttributes
import net.minecraft.world.entity.ai.attributes.AttributeBase
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.craftbukkit.v1_18_R1.CraftEquipmentSlot
import org.bukkit.craftbukkit.v1_18_R1.attribute.CraftAttributeInstance
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.UUID
import net.minecraft.world.entity.ai.attributes.AttributeModifier as AttributeModifierNms

object ItemAttributes : AbstractItemAttributes {
    private val availableEquipmentSlots by lazy {
        EquipmentSlot.values()
    }
    private val availableAttributes by lazy {
        Attribute.values()
    }

    override fun cloneWithDefaultAttributes(itemStack: ItemStack): ItemStack {
        val itemMeta = itemStack.itemMeta ?: return itemStack
        availableEquipmentSlots.forEach { slot ->
            handleEquipmentSlot(itemStack, slot, itemMeta)
        }
        val cloned = itemStack.clone()
        cloned.itemMeta = itemMeta
        return cloned
    }

    private fun handleEquipmentSlot(
        itemStack: ItemStack,
        slot: EquipmentSlot,
        itemMeta: ItemMeta
    ) {
        CraftItemStack.asNMSCopy(itemStack).a(CraftEquipmentSlot.getNMS(slot)).asMap().entries.forEach { entry ->
            updateItemMeta(entry, itemMeta, slot)
        }
    }

    private fun updateItemMeta(
        entry: MutableMap.MutableEntry<AttributeBase, MutableCollection<AttributeModifierNms>>,
        itemMeta: ItemMeta,
        slot: EquipmentSlot
    ) {
        val attr: Attribute = attributeFromString(entry.key.c()) ?: return
        entry.value.forEach { nmsMod ->
            val mod = CraftAttributeInstance.convert(nmsMod)
            itemMeta.removeAttributeModifier(attr)
            val clonedAttributeModifier =
                AttributeModifier(UUID.randomUUID(), mod.name, mod.amount, mod.operation, slot)
            itemMeta.addAttributeModifier(attr, clonedAttributeModifier)
        }
    }

    private fun attributeFromString(name: String): Attribute? = availableAttributes.find { name.endsWith(it.key.key) }
}
