/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class EncodedStringCache(
    @kotlinx.serialization.Transient
    @kotlin.jvm.Transient
    private var cache: String? = null,
    private var string: String = ""
) : SelfSerializable, Comparable<EncodedStringCache> {

    constructor(s: String) : this() {
        set(s)
    }

    override fun compareTo(other: EncodedStringCache): Int = this.get().compareTo(other.get())

    override fun equals(other: Any?): Boolean =
        if (other !is EncodedStringCache) false
        else other.get() == this.get()

    fun get(): String = if (cache != null) cache as String
    else CryptographyUtils.decodeString(string).also { cache = it }

    fun set(s: String) {
        cache = null
        string = CryptographyUtils.encodeString(s)
    }

    companion object {
        @JvmStatic
        fun cached(s: String): EncodedStringCache = EncodedStringCache(s)
    }

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = "EncodedStringCache"
}

fun String.cached(): EncodedStringCache = EncodedStringCache.cached(this)