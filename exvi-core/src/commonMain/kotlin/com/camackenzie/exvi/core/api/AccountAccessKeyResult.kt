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
class AccountAccessKeyResult(val result: EncodedStringCache) : GenericDataResult(uid) {

    val accessKey: String
        get() = this.result.get()

    constructor(result: String) : this(result.cached())

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        @kotlin.jvm.JvmField
        val uid = "AccountAccessKeyResult"
    }
}