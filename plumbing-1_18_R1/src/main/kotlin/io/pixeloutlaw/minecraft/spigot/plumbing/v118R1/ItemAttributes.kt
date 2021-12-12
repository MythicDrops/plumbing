package io.pixeloutlaw.minecraft.spigot.plumbing.v118R1

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractItemAttributes
import net.minecraft.world.entity.EnumItemSlot
import net.minecraft.world.entity.ai.attributes.AttributeBase
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.GenericAttributes
import org.bukkit.attribute.Attribute
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object ItemAttributes : AbstractItemAttributes {
    private val nmsAttributeList = listOf(
        GenericAttributes.a,
        GenericAttributes.b,
        GenericAttributes.c,
        GenericAttributes.d,
        GenericAttributes.e,
        GenericAttributes.f,
        GenericAttributes.g,
        GenericAttributes.h,
        GenericAttributes.i,
        GenericAttributes.j,
        GenericAttributes.k,
        GenericAttributes.l,
        GenericAttributes.m
    )
    private val bukkitAttributeList = Attribute.values().toList()
    private val genericAttributeToAttributeMapping = nmsAttributeList.mapNotNull { nmsAttribute ->
        val matchingBukkitAttribute = bukkitAttributeList.find { bukkitAttribute ->
            nmsAttribute.c() == bukkitAttribute.key.key
        } ?: return@mapNotNull null
        nmsAttribute to matchingBukkitAttribute
    }.toMap()
    private val mainHandSlot = EnumItemSlot.a("mainhand")
    private val offHandSlot = EnumItemSlot.a("offhand")
    private val feetSlot = EnumItemSlot.a("feet")
    private val legsSlot = EnumItemSlot.a("legs")
    private val chestSlot = EnumItemSlot.a("chest")
    private val headSlot = EnumItemSlot.a("head")

    override fun getDefaultItemAttributes(
        itemStack: ItemStack,
        equipmentSlot: EquipmentSlot
    ): Multimap<Attribute, org.bukkit.attribute.AttributeModifier> {
        val vanillaItemStack = ItemStack(itemStack.type) // create copy with no metadata at all
        val nmsItemStack = CraftItemStack.asNMSCopy(vanillaItemStack)
        val attributeMap = nmsItemStack.a(convertEquipmentSlotToEnumItemSlot(equipmentSlot))
        val defaultAttributeMap = HashMultimap.create<Attribute, org.bukkit.attribute.AttributeModifier>()

        // iterate through each of the known attributes for this version of MC
        nmsAttributeList.forEach { attribute ->
            // if the default list contains the iterated attribute, add any attribute modifiers to the known list
            attributeMap[attribute].let { attributeModifiers ->
                defaultAttributeMap.putAll(
                    convertGenericAttribute(attribute),
                    attributeModifiers.filterNotNull().map { convertNmsAttributeModifier(it, equipmentSlot) }
                )
            }
        }
        return defaultAttributeMap
    }

    private fun convertNmsAttributeModifier(
        nmsAttributeModifier: AttributeModifier,
        equipmentSlot: EquipmentSlot
    ): org.bukkit.attribute.AttributeModifier {
        return org.bukkit.attribute.AttributeModifier(
            nmsAttributeModifier.a(),
            nmsAttributeModifier.b(),
            nmsAttributeModifier.d(),
            convertNmsAttributeModifierOperation(nmsAttributeModifier.c()),
            equipmentSlot
        )
    }

    private fun convertNmsAttributeModifierOperation(
        nmsAttributeModifierOperation: AttributeModifier.Operation
    ): org.bukkit.attribute.AttributeModifier.Operation =
        when (nmsAttributeModifierOperation) {
            AttributeModifier.Operation.a -> {
                org.bukkit.attribute.AttributeModifier.Operation.ADD_NUMBER
            }
            AttributeModifier.Operation.b -> {
                org.bukkit.attribute.AttributeModifier.Operation.ADD_SCALAR
            }
            AttributeModifier.Operation.c -> {
                org.bukkit.attribute.AttributeModifier.Operation.MULTIPLY_SCALAR_1
            }
        }

    private fun convertGenericAttribute(genericAttributes: AttributeBase): Attribute? =
        genericAttributeToAttributeMapping[genericAttributes]

    private fun convertEquipmentSlotToEnumItemSlot(equipmentSlot: EquipmentSlot): EnumItemSlot = when (equipmentSlot) {
        EquipmentSlot.HAND -> mainHandSlot
        EquipmentSlot.OFF_HAND -> offHandSlot
        EquipmentSlot.FEET -> feetSlot
        EquipmentSlot.LEGS -> legsSlot
        EquipmentSlot.CHEST -> chestSlot
        EquipmentSlot.HEAD -> headSlot
    }
}
