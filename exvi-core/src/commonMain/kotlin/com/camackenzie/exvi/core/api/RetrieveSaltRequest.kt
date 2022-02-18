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
class RetrieveSaltRequest : SelfSerializable {
    val username: EncodedStringCache

    constructor(username: String) {
        this.username = username.cached()
    }

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is RetrieveSaltRequest) {
            other.username == username
        } else false
    }

    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        const val uid = "RetrieveSaltRequest"
    }
}