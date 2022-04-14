/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.jvm.JvmStatic
import kotlin.reflect.KProperty

/**
 *
 * @author callum
 */
@Serializable
data class EncodedStringCache(
    @Transient
    @kotlin.jvm.Transient
    private var actual: String? = null,
    private var encoded: String = ""
) : SelfSerializable<EncodedStringCache>, Comparable<EncodedStringCache> {

    constructor(s: String) : this() {
        set(s)
    }

    override fun compareTo(other: EncodedStringCache): Int = this.get().compareTo(other.get())

    override fun equals(other: Any?): Boolean =
        if (other !is EncodedStringCache) false
        else other.get() == this.get()

    fun getEncoded(): String = encoded

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = get()
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) = set(value)

    fun get(): String = if (actual != null) actual as String
    else decode(encoded).also { actual = it }

    fun set(s: String) {
        actual = null
        encoded = encode(s)
    }

    companion object {
        @JvmStatic
        fun cached(string: String) = EncodedStringCache(string)

        @JvmStatic
        fun fromEncoded(encoded: String) = EncodedStringCache(encoded = encoded)

        @JvmStatic
        fun decode(encoded: String) = CryptographyUtils.decodeString(encoded)

        @JvmStatic
        fun encode(value: String) = CryptographyUtils.encodeString(value)
    }

    override fun hashCode(): Int {
        var result = actual?.hashCode() ?: 0
        result = 31 * result + encoded.hashCode()
        return result
    }

    override val serializer: KSerializer<EncodedStringCache>
        get() = serializer()
}

fun String.cached(): EncodedStringCache = EncodedStringCache.cached(this)
fun CharSequence.cached(): EncodedStringCache = EncodedStringCache.cached(this.toString())