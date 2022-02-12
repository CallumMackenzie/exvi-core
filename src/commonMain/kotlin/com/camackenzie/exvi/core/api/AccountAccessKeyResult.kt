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
class AccountAccessKeyResult : DataResult<EncodedStringCache>, SelfSerializable {
    override val result: EncodedStringCache?

    constructor(
        error: Int,
        message: String,
        accessKey: String
    ) : super(error, message) {
        result = accessKey.cached()
    }

    constructor(msg: String, key: String) : this(0, msg, key) {}
    constructor(err: Int, msg: String) : this(err, msg, "") {}

    val accessKey: String
        get() = this.result!!.get()

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return "AccountAccessKeyResult"
    }
}