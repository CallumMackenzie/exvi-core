/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model


/**
 *
 * @author callum
 */
object EnumUtils {
    inline fun <reified T : Enum<T>> enumFromString(st: String): T? {
        for (enm in enumValues<T>()) {
            if (enm.toString().equals(st, ignoreCase = true)) {
                return enm
            }
        }
        return null
    }

    fun formatName(superStr: String): String {
        return superStr.lowercase().replace("_".toRegex(), " ")
    }
}