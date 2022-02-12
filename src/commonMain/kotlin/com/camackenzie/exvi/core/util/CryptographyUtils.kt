/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import com.soywiz.krypto.sha256
import com.soywiz.krypto.encoding.toBase64
import com.soywiz.krypto.encoding.fromBase64
import com.soywiz.krypto.SecureRandom
import com.soywiz.krypto.AES
import com.soywiz.krypto.Padding
import com.soywiz.krypto.encoding.Base64
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
object CryptographyUtils {

    fun encryptAES(s: String): EncryptionResult {
        val bytes = s.encodeToByteArray()
        val key = CachedKey(SecureRandom.nextBytes(32).toBase64())
        val encrypted = AES.encryptAes128Cbc(bytes, key.getKeyAsBytes(), Padding.ZeroPadding)
        return EncryptionResult(encrypted.toBase64(), key)
    }

    fun decryptAES(er: EncryptionResult): String {
        val encrypted = Base64.decode(er.encrypted)
        val decrypted = AES.decryptAes128Cbc(encrypted, er.key.getKeyAsBytes(), Padding.ZeroPadding)
        return decrypted.decodeToString()
    }

    fun encodeStringToBase64(s: String): String {
        return s.encodeToByteArray().toBase64()
    }

    fun decodeStringFromBase64(s: String): String {
        return Base64.decode(s).decodeToString()
    }

    fun applyRotationCipher(s: String, rotation: Int): String {
        return applyDynamicRotationCipher(s) { _ -> rotation }
    }

    fun revertRotationCipher(s: String, rot: Int): String {
        return applyRotationCipher(s, -rot)
    }

    fun applyDynamicRotationCipher(s: String, fn: (Int) -> Int): String {
        val ret = StringBuilder()
        for (i in s.indices) {
            ret.append((s[i].code + fn(i)).toChar())
        }
        return ret.toString()
    }

    fun revertDynamicRotationCipher(s: String, fn: (Int) -> Int): String {
        return applyDynamicRotationCipher(s) { i -> -fn(i) }
    }

    fun hashSHA256(s: String): String {
        val bytes = s.encodeToByteArray()
        return bytes.sha256().base64
    }

    fun generateSalt(nBytes: Int): String {
        return SecureRandom.nextBytes(nBytes).toBase64()
    }

    fun generateSalt(): String {
        return generateSalt((SecureRandom.nextDouble() * 10 + 6).toInt() shl 1)
    }

    private fun encodeCipher(i: Int, s2: String): Int {
        return (i / s2.length.toDouble() * 10).toInt()
    }

    fun encodeString(s: String): String {
        val er: EncryptionResult = encryptAES(s)
        val s0 = Json.encodeToString(er)
        val s1 = applyDynamicRotationCipher(s0) { i -> i % 6 }
        val s2 = encodeStringToBase64(s1)
        val s3 = applyDynamicRotationCipher(s2) { i -> encodeCipher(i, s2) }
        val s4 = encodeStringToBase64(s3)
        return applyRotationCipher(s4, 1)
    }

    fun decodeString(s: String): String {
        val s4 = revertRotationCipher(s, 1)
        val s3: String = decodeStringFromBase64(s4)
        val s2 =
            revertDynamicRotationCipher(s3) { i -> encodeCipher(i, s3) }
        val s1: String = decodeStringFromBase64(s2)
        val s0 = revertDynamicRotationCipher(s1) { i -> i % 6 }
        val er: EncryptionResult = Json.decodeFromString(s0)
        return decryptAES(er)
    }
}
