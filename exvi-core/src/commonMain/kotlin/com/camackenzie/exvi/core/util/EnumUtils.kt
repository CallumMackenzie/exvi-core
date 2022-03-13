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
    fun formatName(superStr: String): String =
        superStr.lowercase().replace("_".toRegex(), " ")
}