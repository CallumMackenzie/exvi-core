/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.None
import com.camackenzie.exvi.core.util.SelfSerializable
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
open class GenericDataResult<T : SelfSerializable> : DataResult<T> {
    val responder: EncodedStringCache
    override val result: T?

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    constructor(err: Int, msg: String, resp: T) : super(err, msg) {
        responder = resp.getUID().cached()
        result = resp
    }

    constructor(resp: T) : this(0, "Success", resp) {}

    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun <T : SelfSerializable> success(resp: T): GenericDataResult<T> {
            return GenericDataResult(resp)
        }

        @kotlin.jvm.JvmStatic
        fun faliure(msg: String, err: Int): GenericDataResult<None> {
            return GenericDataResult(err, msg, None)
        }

        @kotlin.jvm.JvmStatic
        val uid = "GenericDataResult"
    }
}