/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.SelfSerializable
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.json.*
import kotlinx.serialization.*


/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class VerificationRequest : SelfSerializable {
    val username: EncodedStringCache
    val email: EncodedStringCache
    val phone: EncodedStringCache

    constructor(
        username: String,
        email: String,
        phone: String
    ) {
        this.username = username.cached()
        this.email = email.cached()
        this.phone = phone.cached()
    }

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
            return "VerificationRequest"
        }
    }
}