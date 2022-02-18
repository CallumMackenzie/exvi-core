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
class LoginRequest : SelfSerializable {
    val username: EncodedStringCache
    val passwordHash: EncodedStringCache

    constructor(username: String, passwordHash: String) {
        this.username = username.cached()
        this.passwordHash = passwordHash.cached()
    }

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        @kotlin.jvm.JvmField
        val uid = "LoginRequest"
    }
}