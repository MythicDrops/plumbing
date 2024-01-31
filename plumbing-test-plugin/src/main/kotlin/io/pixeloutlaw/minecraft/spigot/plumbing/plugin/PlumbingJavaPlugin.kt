package io.pixeloutlaw.minecraft.spigot.plumbing.plugin

import io.pixeloutlaw.kindling.Log
import io.pixeloutlaw.minecraft.spigot.plumbing.api.AbstractMessageBroadcaster
import io.pixeloutlaw.minecraft.spigot.plumbing.api.MinecraftVersions
import io.pixeloutlaw.minecraft.spigot.plumbing.lib.GlowEnchantment
import io.pixeloutlaw.minecraft.spigot.plumbing.lib.ItemAttributes
import io.pixeloutlaw.minecraft.spigot.plumbing.lib.MessageBroadcaster
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockDamageEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.Locale
import java.util.logging.FileHandler
import java.util.logging.Level

public class PlumbingJavaPlugin : JavaPlugin() {
    private lateinit var bukkitAudiences: BukkitAudiences
    private lateinit var glowEnchantment: Enchantment

    override fun onEnable() {
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            logger.severe("Unable to create data folder - disabling Plumbing!")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        if (!MinecraftVersions.isAtLeastMinecraft117) {
            logger.severe("Plumbing requires Minecraft 1.17+ - disabling Plumbing!")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        JulLoggerFactory.clearCachedLoggers()
        JulLoggerFactory.clearCustomizers()
        JulLoggerFactory.registerLoggerCustomizer { logger ->
            logger.apply {
                level = Level.ALL
                addHandler(
                    FileHandler(
                        String.format(
                            "%s/%s.log",
                            dataFolder.absolutePath,
                            this@PlumbingJavaPlugin.name.lowercase(Locale.getDefault()),
                        ),
                        true,
                    ).apply {
                        level = Level.ALL
                        formatter = PlumbingLoggingFormatter()
                    },
                )
                useParentHandlers = false
            }
        }
        Log.addLogger(PlumbingLogger(Log.Level.DEBUG))

        bukkitAudiences = BukkitAudiences.create(this)
        glowEnchantment =
            try {
                requireNotNull(GlowEnchantment.enchantment)
            } catch (ex: Exception) {
                Log.error("Unable to determine appropriate glow enchantment", ex)
                Bukkit.getPluginManager().disablePlugin(this)
                return
            }

        Bukkit.getPluginManager().registerEvents(
            object : Listener {
                @EventHandler
                fun onBlockDamageEvent(event: BlockDamageEvent) {
                    event.player.equipment?.itemInMainHand?.let { mainHand ->
                        MessageBroadcaster.broadcastItem(
                            "%player% damaged a block with a %item%",
                            event.player,
                            mainHand,
                            bukkitAudiences,
                            AbstractMessageBroadcaster.BroadcastTarget.SERVER,
                        )
                    }
                }

                @EventHandler
                fun onPlayerJoinEvent(event: PlayerJoinEvent) {
                    event.player.inventory.addItem(ItemStack(Material.DIAMOND_SWORD))
                    event.player.inventory.addItem(ItemAttributes.cloneWithDefaultAttributes(ItemStack(Material.DIAMOND_SWORD)))
                    event.player.inventory.addItem(ItemStack(Material.DIAMOND_SWORD).also { it.addEnchantment(glowEnchantment, 1) })
                }
            },
            this,
        )
    }

    override fun onDisable() {
        bukkitAudiences.close()
        HandlerList.unregisterAll(this)
        Bukkit.getScheduler().cancelTasks(this)
        Log.clearLoggers()
    }
}
