/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import kotlin.jvm.Transient
import com.camackenzie.exvi.core.util.EncryptionResult

/**
 *
 * @author callum
 */
class EncodedStringCache(str: String) {
    private var string: String

    @Transient
    private var cache: String? = null

    init {
        this.set(str)
    }

    fun get(): String {
        return if (cache != null) cache
        else CryptographyUtils.decodeString(string).also { cache = it }
    }

    fun set(s: String?) {
        cache = null
        string = CryptographyUtils.encodeString(`val`)
    }
}