/*
 * The MIT License
 * Copyright © 2015 Pixel Outlaw
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.pixeloutlaw.minecraft.spigot.plumbing.api

import org.bukkit.ChatColor
import java.util.Locale

private val chatColorReplacementMap =
    ChatColor.entries.flatMap {
        listOf(
            "<${it.name}>" to it,
            "<${it.name.replace("_", " ")}>" to it,
            "<${it.name.replace("_", "")}>" to it,
            it.toString().uppercase(Locale.getDefault()).replace(ChatColor.COLOR_CHAR, '&') to it,
            it.toString().lowercase(Locale.getDefault()).replace(ChatColor.COLOR_CHAR, '&') to it,
        )
    }.toMap()
private val whiteSpaceRegex = "\\s+".toRegex()

/**
 * Replaces all arguments (first item in pair) with their values (second item in pair).
 *
 * @param args Pairs of arguments to replace
 * @return copy of [String] with arguments replaced
 */
public fun String.replaceArgs(args: Iterable<Pair<String, String>>): String =
    args.fold(this) { acc, pair -> acc.replace(pair.first, pair.second) }

/**
 * Replaces all ampersands with [ChatColor.COLOR_CHAR] and all instances of two [ChatColor.COLOR_CHAR] with ampersands.
 */
public fun String.chatColorize(): String =
    chatColorReplacementMap.entries.fold(this) { acc, entry -> acc.replace(entry.key, entry.value.toString()) }

/**
 * Replaces all [ChatColor.COLOR_CHAR] with ampersands.
 */
public fun String.unChatColorize(): String = this.replace('\u00A7', '&')

/**
 * Strips the colors from the String.
 */
public fun String.stripColors(): String = ChatColor.stripColor(this)!! // using double bangs because `this` cannot be null

/**
 * Returns true if the String starts with any of the provided [list].
 */
@JvmOverloads
public fun String.startsWithAny(
    list: List<String>,
    ignoreCase: Boolean = false,
): Boolean {
    return list.any { this.startsWith(it, ignoreCase) }
}

/**
 * Returns true if the String ends with any of the provided [list].
 */
@JvmOverloads
public fun String.endsWithAny(
    list: List<String>,
    ignoreCase: Boolean = false,
): Boolean {
    return list.any { this.endsWith(it, ignoreCase) }
}

/**
 * Converts a string to Title Case.
 *
 * Example: "a boy went to the park".toTitleCase() == "A Boy Went To The Park"
 */
public fun String.toTitleCase(): String {
    return split(whiteSpaceRegex).joinToString(separator = " ") {
        if (it.length > 1) {
            "${it.substring(0, 1).uppercase(Locale.getDefault())}${it.substring(1).lowercase(Locale.getDefault())}"
        } else {
            it.uppercase(Locale.getDefault())
        }
    }
}
