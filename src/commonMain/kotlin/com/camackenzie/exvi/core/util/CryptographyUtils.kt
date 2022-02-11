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

/**
 *
 * @author callum
 */
object CryptographyUtils {

    fun encryptAES(s: String): EncryptionResult {
        // TODO
    }

    fun decryptAES(er: EncryptionResult): String {
        // TODO
    }

    fun encodeStringToBase64(s: String): String {
        return s.encodeToByteArray().toBase64()
    }

    fun applyRotationCipher(s: String, rotation: Int): String {
        return applyDynamicRotationCipher(s, { i: Int -> rotation })
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
        return String(s.toByteArray(Charsets.UTF_8).sha256(), Charsets.UTF_8)
    }

    fun generateSalt(nBytes: Int): String {
        return SecureRandom.nextBytes(nBytes).toBase64()
    }

    fun generateSalt(): String {
        return generateSalt((SecureRandom.nextDouble() * 10 + 6).toInt() shl 1)
    }

    fun encodeString(s: String): String {
        // TODO
    }

    fun decodeString(s: String): String {
        // TODO
    }
}