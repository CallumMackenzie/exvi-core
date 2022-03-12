/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import com.soywiz.krypto.encoding.Base64

/**
 *
 * @author callum
 */
@Serializable
data class CachedKey(val key: String) {
    fun getKeyAsBytes(): ByteArray = Base64.decode(key)
}