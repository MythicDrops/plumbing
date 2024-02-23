package io.pixeloutlaw.minecraft.spigot.plumbing.plugin

import java.util.logging.Logger
import kotlin.reflect.KClass

internal object JulLoggerFactory {
    private val cachedLoggers = mutableMapOf<String, Logger>()
    private val loggerCustomizers = mutableListOf<JulLoggerCustomizer>()

    fun getLogger(clazz: KClass<*>) = getLogger(clazz.java.canonicalName)

    /**
     * Registers a [JulLoggerCustomizer] to customize a [Logger].
     *
     * @param customizer customizer
     */
    fun registerLoggerCustomizer(customizer: JulLoggerCustomizer) {
        loggerCustomizers.add(customizer)
    }

    fun clearCustomizers() {
        loggerCustomizers.clear()
    }

    fun clearCachedLoggers() {
        // clear handlers
        cachedLoggers.values.forEach { cachedLogger ->
            cachedLogger.handlers.toList().forEach { handler ->
                handler.close()
                cachedLogger.removeHandler(handler)
            }
        }

        // clear loggers
        cachedLoggers.clear()
    }

    private fun getLogger(name: String) =
        cachedLoggers.getOrPut(name) {
            loggerCustomizers.fold(
                Logger.getLogger(name),
            ) { logger, customizer ->
                customizer.customize(logger)
            }
        }
}
