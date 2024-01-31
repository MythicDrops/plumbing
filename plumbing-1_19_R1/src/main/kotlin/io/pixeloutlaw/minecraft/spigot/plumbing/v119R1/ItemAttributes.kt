package io.pixeloutlaw.minecraft.spigot.plumbing.v119R1

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractItemAttributes
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.craftbukkit.v1_19_R1.CraftEquipmentSlot
import org.bukkit.craftbukkit.v1_19_R1.attribute.CraftAttributeInstance
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.UUID
import net.minecraft.world.entity.ai.attributes.Attribute as AttributeNms
import net.minecraft.world.entity.ai.attributes.AttributeModifier as AttributeModifierNms

public object ItemAttributes : AbstractItemAttributes() {
    override val availableEquipmentSlots: List<EquipmentSlot> by lazy {
        EquipmentSlot.entries
    }
    override val availableAttributes: List<Attribute> by lazy {
        Attribute.entries
    }

    override fun handleEquipmentSlot(
        itemStack: ItemStack,
        slot: EquipmentSlot,
        itemMeta: ItemMeta,
    ) {
        CraftItemStack.asNMSCopy(itemStack)
            .getAttributeModifiers(CraftEquipmentSlot.getNMS(slot))
            .asMap()
            .entries
            .forEach { entry ->
                updateItemMeta(entry, itemMeta, slot)
            }
    }

    private fun updateItemMeta(
        entry: MutableMap.MutableEntry<AttributeNms, MutableCollection<AttributeModifierNms>>,
        itemMeta: ItemMeta,
        slot: EquipmentSlot,
    ) {
        val attr: Attribute = attributeFromString(entry.key.descriptionId) ?: return
        entry.value.forEach { nmsMod ->
            val mod = CraftAttributeInstance.convert(nmsMod)
            itemMeta.removeAttributeModifier(attr)
            val clonedAttributeModifier =
                AttributeModifier(UUID.randomUUID(), mod.name, mod.amount, mod.operation, slot)
            itemMeta.addAttributeModifier(attr, clonedAttributeModifier)
        }
    }
}
