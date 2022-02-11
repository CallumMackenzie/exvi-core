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
import com.soywiz.krypto.encoding.Base64
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
object CryptographyUtils {

    fun encryptAES(s: String): EncryptionResult {
        val bytes = s.toByteArray(Charsets.UTF_8)
        val key = CachedKey(SecureRandom.nextBytes(128).toBase64())
        val encrypted = AES.encryptAes128Cbc(bytes, key.getKeyAsBytes())
        return EncryptionResult(encrypted.toBase64(), key)
    }

    fun decryptAES(er: EncryptionResult): String {
        val encrypted = Base64.decode(er.encrypted)
        val decrypted = AES.decryptAes128Cbc(encrypted, er.key.getKeyAsBytes())
        return decrypted.toString(Charsets.UTF_8)
    }

    fun encodeStringToBase64(s: String): String {
        return s.toByteArray(Charsets.UTF_8).toBase64()
    }

    fun decodeStringFromBase64(s: String) : String {
        return Base64.decode(s).toString(Charsets.UTF_8)
    }

    fun applyRotationCipher(s: String, rotation: Int): String {
        return applyDynamicRotationCipher(s, { _ -> rotation })
    }

    fun revertRotationCipher(s: String, rot: Int): String {
        return applyRotationCipher(s, -rot)
    }

    fun applyDynamicRotationCipher(s: String, fn: (Int) -> Int): String {
        val ret = StringBuilder()
        for (i in 0 until s.length) {
            ret.append((s[i].code + fn(i)).toChar())
        }
        return ret.toString()
    }

    fun revertDynamicRotationCipher(s: String, fn: (Int) -> Int): String {
        return applyDynamicRotationCipher(s, { i -> -fn(i) })
    }

    fun hashSHA256(s: String): String {
        val bytes = s.toByteArray(Charsets.UTF_8)
        return bytes.sha256().base64
    }

    fun generateSalt(nBytes: Int): String {
        return SecureRandom.nextBytes(nBytes).toBase64()
    }

    fun generateSalt(): String {
        return generateSalt((SecureRandom.nextDouble() * 10 + 6).toInt() shl 1)
    }

    fun encodeString(s: String): String {
        val er = encryptAES(s)
        val s0 = Json.encodeToString(er)
        val s1 = applyDynamicRotationCipher(s0) { i -> i % 12 }
        val s2 = encodeStringToBase64(s1)
        val s3 = applyDynamicRotationCipher(s2) { i -> (i / s2.length.toDouble() * 10).toInt() }
        val s4 = encodeStringToBase64(s3)
        return applyRotationCipher(s4, 1)
    }

    fun decodeString(s: String): String {
        val s4 = revertRotationCipher(s, 1)
        val s3: String = decodeStringFromBase64(s4)
        val s2 =
            revertDynamicRotationCipher(s3) { i -> (i / s3.length.toDouble() * 10).toInt() }
        val s1: String = decodeStringFromBase64(s2)
        val s0 = revertDynamicRotationCipher(s1) { i -> i % 12 }
        val er: EncryptionResult = Json.decodeFromString<EncryptionResult>(s0)
        return decryptAES(er)
    }
}