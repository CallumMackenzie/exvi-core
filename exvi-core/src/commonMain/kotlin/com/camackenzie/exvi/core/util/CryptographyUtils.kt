/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import com.camackenzie.exvi.core.model.ExviSerializer
import com.soywiz.krypto.AES
import com.soywiz.krypto.Padding
import com.soywiz.krypto.SecureRandom
import com.soywiz.krypto.encoding.Base64
import com.soywiz.krypto.encoding.toBase64
import com.soywiz.krypto.sha256
import kotlin.jvm.JvmStatic

/**
 *
 * @author callum
 */
@Suppress("unused")
object CryptographyUtils {

    @JvmStatic
    fun encryptAES(s: String): EncryptionResult {
        val bytes = s.encodeToByteArray()
        val key = CachedKey.generateKey()
        val encrypted = AES.encryptAes128Cbc(bytes, key.getKeyAsBytes(), Padding.ZeroPadding)
        return EncryptionResult(encrypted.toBase64(), key)
    }

    @JvmStatic
    fun decryptAES(er: EncryptionResult): String {
        val encrypted = Base64.decode(er.encrypted)
        val decrypted = AES.decryptAes128Cbc(encrypted, er.key.getKeyAsBytes(), Padding.ZeroPadding)
        return decrypted.decodeToString()
    }

    @JvmStatic
    fun encodeStringToBase64(s: String): String = s.encodeToByteArray().toBase64()

    @JvmStatic
    fun decodeStringFromBase64(s: String): String = Base64.decode(s).decodeToString()

    @JvmStatic
    fun applyRotationCipher(s: String, rotation: Int): String =
        applyDynamicRotationCipher(s) { rotation }

    @JvmStatic
    fun revertRotationCipher(s: String, rot: Int): String =
        applyRotationCipher(s, -rot)

    @JvmStatic
    fun applyDynamicRotationCipher(s: String, fn: (Int) -> Int): String {
        val ret = StringBuilder()
        for (i in s.indices) {
            ret.append((s[i].code + fn(i)).toChar())
        }
        return ret.toString()
    }

    @JvmStatic
    fun revertDynamicRotationCipher(s: String, fn: (Int) -> Int): String =
        applyDynamicRotationCipher(s) { i -> -fn(i) }

    @JvmStatic
    fun hashSHA256(s: String): String {
        val bytes = s.encodeToByteArray()
        return bytes.sha256().base64
    }

    @JvmStatic
    fun generateSalt(nBytes: Int): String = SecureRandom.nextBytes(nBytes).toBase64()

    @JvmStatic
    fun generateSalt(): String = generateSalt((SecureRandom.nextDouble() * 10 + 6).toInt() shl 1)

    @JvmStatic
    private fun encodeCipher(i: Int, s2: String): Int = (i / s2.length.toDouble() * 10).toInt()

    @JvmStatic
    fun tryDecode(value: String): String? = try {
        decodeString(value)
    } catch (e: Throwable) {
        null
    }

    @JvmStatic
    fun decodeOr(value: String, default: String): String = tryDecode(value) ?: default

    @JvmStatic
    fun decodeOrValue(value: String): String = decodeOr(value, value)

    private const val ExviEncodedPrefix = "EXVI.ENCODED"

    @JvmStatic
    fun encodeString(s: String): String {
        val s0 = "$ExviEncodedPrefix$s"
        val s1: EncryptionResult = encryptAES(s0)
        val s2 = generateSalt(3) + ExviSerializer.toJson(s1) + generateSalt(3)
        return encodeStringToBase64(s2)
    }

    @JvmStatic
    fun decodeString(s: String): String {
        val s4 = decodeStringFromBase64(s)
        val s3 = s4.substring(4, s4.length - 4)
        val s2: EncryptionResult = ExviSerializer.fromJson(s3)
        val s1 = decryptAES(s2)
        return if (s1.startsWith(ExviEncodedPrefix))
            s1.substring(ExviEncodedPrefix.length)
        else throw Exception("String does not start with encoded prefix")
    }

    @JvmStatic
    fun bytesToBase64String(byteArray: ByteArray): String = byteArray.toBase64()

    @JvmStatic
    fun bytesFromBase64String(str: String): ByteArray = Base64.decode(str)
}
