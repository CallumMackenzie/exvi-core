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
@Serializable
@Suppress("unused")
data class EncodedStringCache(
    @Transient
    @kotlin.jvm.Transient
    private var actual: String? = null,
    private var encoded: String = ""
) : SelfSerializable, Comparable<EncodedStringCache> {

    constructor(s: String) : this() {
        set(s)
    }

    override fun compareTo(other: EncodedStringCache): Int = this.get().compareTo(other.get())

    override fun equals(other: Any?): Boolean =
        if (other !is EncodedStringCache) false
        else other.get() == this.get()

    fun getEncoded(): String = encoded

    fun get(): String = if (actual != null) actual as String
    else decode(encoded).also { actual = it }

    fun set(s: String) {
        actual = null
        encoded = encode(s)
    }

    companion object {
        @JvmStatic
        fun cached(string: String): EncodedStringCache = EncodedStringCache(string)

        @JvmStatic
        fun fromEncoded(encoded: String): EncodedStringCache = EncodedStringCache(encoded = encoded)

        @JvmStatic
        fun decode(encoded: String): String = CryptographyUtils.decodeString(encoded)

        @JvmStatic
        fun encode(value: String): String = CryptographyUtils.encodeString(value)
    }

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = "EncodedStringCache"

    override fun hashCode(): Int {
        var result = actual?.hashCode() ?: 0
        result = 31 * result + encoded.hashCode()
        return result
    }
}

fun String.cached(): EncodedStringCache = EncodedStringCache.cached(this)
fun CharSequence.cached(): EncodedStringCache = EncodedStringCache.cached(this.toString())