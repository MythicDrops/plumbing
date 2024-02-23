package io.pixeloutlaw.minecraft.spigot.plumbing.api

import org.bukkit.enchantments.Enchantment

/**
 * Utility for holding the glow enchantment for each version of Minecraft.
 */
public abstract class AbstractGlowEnchantment {
    public abstract val enchantment: Enchantment?
}
