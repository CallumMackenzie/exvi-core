/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import com.soywiz.krypto.sha256
import com.soywiz.krypto.encoding.toBase64
import com.soywiz.krypto.SecureRandom
import com.soywiz.krypto.AES
import com.soywiz.krypto.Padding
import com.soywiz.krypto.encoding.Base64
import com.soywiz.krypto.encoding.base64
import io.ktor.util.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
object CryptographyUtils {

    @kotlin.jvm.JvmStatic
    fun encryptAES(s: String): EncryptionResult {
        val bytes = s.encodeToByteArray()
        val key = CachedKey(SecureRandom.nextBytes(32).toBase64())
        val encrypted = AES.encryptAes128Cbc(bytes, key.getKeyAsBytes(), Padding.ZeroPadding)
        return EncryptionResult(encrypted.toBase64(), key)
    }

    @kotlin.jvm.JvmStatic
    fun decryptAES(er: EncryptionResult): String {
        val encrypted = Base64.decode(er.encrypted)
        val decrypted = AES.decryptAes128Cbc(encrypted, er.key.getKeyAsBytes(), Padding.ZeroPadding)
        return decrypted.decodeToString()
    }

    @kotlin.jvm.JvmStatic
    fun encodeStringToBase64(s: String): String = s.encodeToByteArray().toBase64()

    @kotlin.jvm.JvmStatic
    fun decodeStringFromBase64(s: String): String = Base64.decode(s).decodeToString()

    @kotlin.jvm.JvmStatic
    fun applyRotationCipher(s: String, rotation: Int): String =
        applyDynamicRotationCipher(s) { rotation }

    @kotlin.jvm.JvmStatic
    fun revertRotationCipher(s: String, rot: Int): String =
        applyRotationCipher(s, -rot)

    @kotlin.jvm.JvmStatic
    fun applyDynamicRotationCipher(s: String, fn: (Int) -> Int): String {
        val ret = StringBuilder()
        for (i in s.indices) {
            ret.append((s[i].code + fn(i)).toChar())
        }
        return ret.toString()
    }

    @kotlin.jvm.JvmStatic
    fun revertDynamicRotationCipher(s: String, fn: (Int) -> Int): String =
        applyDynamicRotationCipher(s) { i -> -fn(i) }

    @kotlin.jvm.JvmStatic
    fun hashSHA256(s: String): String {
        val bytes = s.encodeToByteArray()
        return bytes.sha256().base64
    }

    @kotlin.jvm.JvmStatic
    fun generateSalt(nBytes: Int): String = SecureRandom.nextBytes(nBytes).toBase64()

    @kotlin.jvm.JvmStatic
    fun generateSalt(): String = generateSalt((SecureRandom.nextDouble() * 10 + 6).toInt() shl 1)

    @kotlin.jvm.JvmStatic
    private fun encodeCipher(i: Int, s2: String): Int {
        return (i / s2.length.toDouble() * 10).toInt()
    }

    @kotlin.jvm.JvmStatic
    fun encodeString(s: String): String {
        val er: EncryptionResult = encryptAES(s)
        val s0 = Json.encodeToString(er)
        return encodeStringToBase64(s0)
    }

    @kotlin.jvm.JvmStatic
    fun decodeString(s: String): String {
        val s0 = decodeStringFromBase64(s)
        val er: EncryptionResult = Json.decodeFromString(s0)
        return decryptAES(er)
    }

    @kotlin.jvm.JvmStatic
    fun bytesToBase64String(byteArray: ByteArray): String = byteArray.toBase64()

    @kotlin.jvm.JvmStatic
    fun bytesFromBase64String(str: String): ByteArray = Base64.decode(str)
}
