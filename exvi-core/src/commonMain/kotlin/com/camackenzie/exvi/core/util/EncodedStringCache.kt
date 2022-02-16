/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class EncodedStringCache : SelfSerializable {

    @kotlinx.serialization.Transient
    private var cache: String? = null

    private var string: String = ""

    constructor(s: String) {
        set(s)
    }

    fun get(): String {
        return if (cache != null) cache as String
        else CryptographyUtils.decodeString(string).also { cache = it }
    }

    fun set(s: String) {
        cache = null
        string = CryptographyUtils.encodeString(s)
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun cached(s: String): EncodedStringCache {
            return EncodedStringCache(s)
        }
    }

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return "EncodedStringCache"
    }
}

fun String.cached(): EncodedStringCache {
    return EncodedStringCache.cached(this)
}