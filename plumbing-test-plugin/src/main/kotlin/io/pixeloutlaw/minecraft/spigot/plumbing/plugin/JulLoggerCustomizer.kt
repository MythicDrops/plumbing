package io.pixeloutlaw.minecraft.spigot.plumbing.plugin

import java.util.logging.Logger

/**
 * Represents a way to customize a [Logger].
 */
internal fun interface JulLoggerCustomizer {
    /**
     * Apply customizations to a [Logger] instance.
     */
    fun customize(logger: Logger): Logger
}
