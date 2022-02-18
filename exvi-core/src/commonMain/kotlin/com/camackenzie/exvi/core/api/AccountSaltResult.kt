/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class AccountSaltResult : DataResult<EncodedStringCache> {
    override val result: EncodedStringCache?

    @kotlin.jvm.JvmOverloads
    constructor(
        err: Int = 0,
        msg: String = "",
        salt: String = ""
    ) : super(err, msg) {
        result = salt.cached()
    }

    val salt: String?
        get() = result?.get()

    override fun toJson(): String {
        return Json.encodeToString(this)
    }


    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        @kotlin.jvm.JvmStatic
        val uid = "AccountSaltResult"
    }
}