/*
 * Copyright (c) Callum Mackenzie 2022.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util


/**
 *
 * @author callum
 */
object EnumUtils {
    inline fun <reified T : Enum<T>> enumFromString(st: String): T? = enumFromStringStrict<T>(st)
        ?: enumFromStringStrict<T>(st.replace("_", " "))
        ?: enumFromStringStrict<T>(st.replace(" ", ""))
        ?: enumFromStringStrict<T>(st.replace("_", ""))

    inline fun <reified T : Enum<T>> enumFromStringStrict(st: String): T? {
        for (enm in enumValues<T>()) {
            if (enm.toString().equals(st, ignoreCase = true)) {
                return enm
            }
        }
        return null
    }

    @kotlin.jvm.JvmStatic
    fun formatName(superStr: String): String {
        // Account for "PascalCase" -> "pascal case"
        val pascalRegex = Regex("[A-Z]")
        var sb = StringBuilder()
        val matches = pascalRegex.findAll(superStr).toList()
        for ((idx, match) in matches.iterator().withIndex()) {
            if (idx != 0)
                sb.append(" ")
            val endIdx = if (idx < matches.size - 1) matches[idx + 1].range.first else superStr.length
            sb.append(superStr.substring(match.range.first, endIdx).lowercase())
        }
        if (matches.isEmpty()) sb.append(superStr)
        // Account for snake case variants "SNAKE_CASE" -> "snake case"
        return sb.toString().lowercase().replace(Regex("_|\\s+"), " ")
    }
}