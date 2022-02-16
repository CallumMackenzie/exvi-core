/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.SelfSerializable
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class GenericDataRequest<T : SelfSerializable>(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val body: T
) : SelfSerializable {

    val requester: EncodedStringCache = body.getUID().cached()

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return Companion.getUID()
    }

    companion object {
        @kotlin.jvm.JvmStatic
        @kotlin.jvm.JvmName("UID")
        fun getUID(): String {
            return "GenericDataRequest"
        }
    }
}