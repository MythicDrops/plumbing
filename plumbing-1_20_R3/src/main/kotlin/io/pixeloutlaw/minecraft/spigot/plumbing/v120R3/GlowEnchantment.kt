package io.pixeloutlaw.minecraft.spigot.plumbing.v120R3

import io.pixeloutlaw.kindling.Log
import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractGlowEnchantment
import io.pixeloutlaw.minecraft.spigot.plumbing.api.Reflection
import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.EnchantmentCategory
import org.bukkit.NamespacedKey
import org.bukkit.craftbukkit.v1_20_R3.enchantments.CraftEnchantment
import org.bukkit.enchantments.Enchantment
import java.util.IdentityHashMap

public object GlowEnchantment : AbstractGlowEnchantment() {
    @Suppress("ktlint:standard:max-line-length", "detekt:MaxLineLength")
    override val enchantment: Enchantment by lazy {
        val registryFrozen = Reflection.getFields(MappedRegistry::class.java).get("l", Boolean::class.java)
        val registryIntrusiveHolders = Reflection.getFields(MappedRegistry::class.java).get("m", java.util.Map::class.java)
        requireNotNull(registryFrozen)
        requireNotNull(registryIntrusiveHolders)

        val holders = registryIntrusiveHolders.get(BuiltInRegistries.ENCHANTMENT)
        Log.info("registryIntrusiveHolders.get(BuildInRegistries.ENCHANTMENT) = $holders")
        if (holders == null) {
            registryIntrusiveHolders.set(BuiltInRegistries.ENCHANTMENT, IdentityHashMap<Any, Any>())
        }

        val wasFrozen = registryFrozen.getBoolean(BuiltInRegistries.ENCHANTMENT)
        registryFrozen.set(BuiltInRegistries.ENCHANTMENT, false)

        val nmsGlowEnchantment = NmsGlowEnchantment()
        Registry.register(BuiltInRegistries.ENCHANTMENT, "plumbing:glow", nmsGlowEnchantment)

        registryIntrusiveHolders.set(BuiltInRegistries.ENCHANTMENT, holders)

        if (wasFrozen) {
            BuiltInRegistries.ENCHANTMENT.freeze()
        }

        object : CraftEnchantment(NamespacedKey("plumbing", "glow"), nmsGlowEnchantment) {
            @Deprecated("Deprecated in Java")
            override fun isCursed(): Boolean = true

            @Deprecated("Deprecated in Java")
            override fun getName(): String = "PlumbingGlow"
        }
    }

    internal class NmsGlowEnchantment : net.minecraft.world.item.enchantment.Enchantment(
        Rarity.COMMON,
        EnchantmentCategory.BREAKABLE,
        EquipmentSlot.entries.toTypedArray(),
    )
}
