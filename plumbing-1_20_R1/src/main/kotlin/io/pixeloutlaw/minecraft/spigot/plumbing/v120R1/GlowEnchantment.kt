package io.pixeloutlaw.minecraft.spigot.plumbing.v120R1

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractGlowEnchantment
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

public object GlowEnchantment : AbstractGlowEnchantment() {
    override val enchantment: Enchantment by lazy {
        val key = NamespacedKey("plumbing", "glow")
        val glowEnchant =
            object : Enchantment(key) {
                override fun canEnchantItem(item: ItemStack): Boolean = true

                override fun getItemTarget(): EnchantmentTarget = EnchantmentTarget.ALL

                @Deprecated("Deprecated in Java")
                override fun getName(): String = "PlumbingGlow"

                @Deprecated("Deprecated in Java")
                override fun isCursed(): Boolean = false

                override fun isTreasure(): Boolean = false

                override fun getMaxLevel(): Int = 1

                override fun getStartLevel(): Int = 1

                override fun conflictsWith(other: Enchantment): Boolean = false
            }
        if (Enchantment.getByKey(key) == null) {
            try {
                val f = Enchantment::class.java.getDeclaredField("acceptingNew")
                f.isAccessible = true
                f[null] = true
            } catch (ignored: Exception) {
            }
            try {
                Enchantment.registerEnchantment(glowEnchant)
            } catch (ignored: IllegalStateException) {
            } catch (ignored: IllegalArgumentException) {
            }
        }

        glowEnchant
    }
}
