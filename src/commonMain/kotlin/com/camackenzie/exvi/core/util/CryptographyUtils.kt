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

    fun encryptAES(`in`: String): EncryptionResult {
        // TODO
    }

    fun decryptAES(er: EncryptionResult): String {
        // TODO
    }

    fun encodeStringToBase64(s: String): String {
        return s.encodeToByteArray().toBase64()
    }

    fun applyRotationCipher(`in`: String, rotation: Int): String {
        return applyDynamicRotationCipher(`in`, java.util.function.Function<Int, Int> { i: Int? -> rotation })
    }

    fun revertRotationCipher(`in`: String, rot: Int): String {
        return applyRotationCipher(`in`, -rot)
    }

    fun applyDynamicRotationCipher(`in`: String, fn: java.util.function.Function<Int?, Int?>): String {
        val ret = StringBuilder()
        for (i in 0 until `in`.length) {
            ret.append((`in`[i].code + fn.apply(i)).toChar())
        }
        return ret.toString()
    }

    fun revertDynamicRotationCipher(`in`: String, fn: java.util.function.Function<Int?, Int?>): String {
        return applyDynamicRotationCipher(`in`, java.util.function.Function<Int, Int> { i: Int? -> -fn.apply(i) })
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

    fun encodeString(`in`: String): String {
        // TODO
    }

    fun decodeString(s5: String): String {
        // TODO
    }
}