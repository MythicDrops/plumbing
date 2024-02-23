package io.pixeloutlaw.minecraft.spigot.plumbing.lib

import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractGlowEnchantment
import io.pixeloutlaw.minecraft.spigot.plumbing.api.MinecraftVersions
import org.bukkit.enchantments.Enchantment

public object GlowEnchantment : AbstractGlowEnchantment() {
    private val glowEnchantmentByServer: AbstractGlowEnchantment by lazy {
        when (MinecraftVersions.nmsVersion) {
            "v1_20_R3" -> io.pixeloutlaw.minecraft.spigot.plumbing.v120R3.GlowEnchantment
            "v1_20_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v120R2.GlowEnchantment
            "v1_20_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v120R1.GlowEnchantment
            "v1_19_R3" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R3.GlowEnchantment
            "v1_19_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R2.GlowEnchantment
            "v1_19_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v119R1.GlowEnchantment
            "v1_18_R2" -> io.pixeloutlaw.minecraft.spigot.plumbing.v118R2.GlowEnchantment
            "v1_18_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v118R1.GlowEnchantment
            "v1_17_R1" -> io.pixeloutlaw.minecraft.spigot.plumbing.v117R1.GlowEnchantment
            else -> GlowEnchantment.NoOpGlowEnchantment
        }
    }

    override val enchantment: Enchantment? = glowEnchantmentByServer.enchantment

    internal object NoOpGlowEnchantment : AbstractGlowEnchantment() {
        override val enchantment: Enchantment? = null
    }
}
