/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import kotlin.jvm.Transient

/**
 *
 * @author callum
 */
class EncodedStringCache(`val`: String?) {
    private var string: String? = null

    @Transient
    private var cache: String? = null

    init {
        this.set(`val`)
    }

    fun get(): String? {
        return if (cache != null) {
            cache
        } else com.camackenzie.exvi.core.util.CryptographyUtils.decodeString(string).also { cache = it }
    }

    fun set(`val`: String?) {
        cache = null
        string = com.camackenzie.exvi.core.util.CryptographyUtils.encodeString(`val`)
    }
}