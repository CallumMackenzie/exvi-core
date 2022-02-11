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
@kotlinx.serialization.Serializable
class EncodedStringCache(var value: String) {

    @kotlinx.serialization.Transient
    private var cache: String? = null

    fun get(): String {
        return if (cache != null) cache as String
        else CryptographyUtils.decodeString(value).also { cache = it }
    }

    fun set(s: String) {
        cache = null
        value = CryptographyUtils.encodeString(s)
    }
}