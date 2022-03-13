/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import com.soywiz.krypto.SecureRandom
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import com.soywiz.krypto.encoding.Base64
import com.soywiz.krypto.encoding.toBase64
import kotlin.jvm.JvmStatic

/**
 *
 * @author callum
 */
@Serializable
data class CachedKey(val key: String) {
    fun getKeyAsBytes(): ByteArray = Base64.decode(key)

    val bytes
        get() = getKeyAsBytes()

    companion object {
        @JvmStatic
        fun generateKey(): CachedKey = generateKey(32)

        @JvmStatic
        fun generateKey(nBytes: Int) = CachedKey(SecureRandom.nextBytes(nBytes).toBase64())
    }
}