package io.pixeloutlaw.minecraft.spigot.plumbing.api

import io.pixeloutlaw.kindling.Log
import java.lang.reflect.Field

/**
 * Port of Denizen's reflection utils to Kotlin: https://github.com/DenizenScript/Denizen-Core/blob/master/src/main/java/com/denizenscript/denizencore/utilities/ReflectionHelper.java
 */
@Suppress("ktlint:standard:max-line-length", "detekt:MaxLineLength")
public object Reflection {
    public class CheckingFieldMap(public val clazz: Class<*>) : HashMap<String, Field>() {
        public fun getFirstOfType(fieldClazz: Class<*>): Field? {
            return super.values.firstOrNull { it.type == fieldClazz }.also {
                if (it == null) {
                    Log.error(
                        "Reflection field missing - tried to find field of type '${fieldClazz.canonicalName}' of class '${clazz.canonicalName}'",
                    )
                }
            }
        }

        public override fun get(key: String): Field? {
            val f = super.get(key)
            if (f == null) {
                Log.error("Reflection field missing - tried to find field '$key' of class '${clazz.canonicalName}'")
            }
            return f
        }

        public fun get(
            name: String,
            expected: Class<*>,
        ): Field? {
            val f = get(name) ?: return null
            if (f.type != expected) {
                Log.error(
                    "Reflection field incorrect type - read field '$name' from class '${clazz.canonicalName}', expected type '${ expected.canonicalName}' but is type '${f.type.canonicalName}'",
                )
            }
            return f
        }

        public fun getNoCheck(name: String): Field? {
            return super.get(name)
        }
    }

    private val cachedFields: MutableMap<Class<*>, CheckingFieldMap> = mutableMapOf()

    public fun getFields(clazz: Class<*>): CheckingFieldMap {
        var fields = cachedFields[clazz]
        if (fields != null) {
            return fields
        }
        fields = CheckingFieldMap(clazz)
        for (field in clazz.declaredFields) {
            field.isAccessible = true
            fields[field.name] = field
        }
        cachedFields[clazz] = fields
        return fields
    }
}
