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
@Serializable
@Suppress("unused")
class AccountCreationRequest : SelfSerializable {
    val username: EncodedStringCache
    val verificationCode: EncodedStringCache
    val password: EncodedStringCache

    constructor(
        username: String,
        verificationCode: String,
        password: String
    ) {
        this.username = username.cached()
        this.verificationCode = verificationCode.cached()
        this.password = password.cached()
    }

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    companion object {
        const val uid = "AccountCreationRequest"
    }
}