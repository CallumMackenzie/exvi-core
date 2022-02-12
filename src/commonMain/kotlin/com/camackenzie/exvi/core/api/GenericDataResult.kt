/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.SelfSerializable
import com.camackenzie.exvi.core.util.cached

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
open class GenericDataResult<T : SelfSerializable> : DataResult<T> {
    val responder: EncodedStringCache

    constructor(err: Int, msg: String, resp: T?) : super(err, msg, resp) {
        responder = resp!!::class.qualifiedName!!.cached()
    }

    constructor(resp: T) : this(0, "Success", resp) {}

    companion object {
        fun <T : SelfSerializable> success(resp: T): GenericDataResult<T> {
            return GenericDataResult(resp)
        }

        fun <T : SelfSerializable> faliure(msg: String, err: Int): GenericDataResult<T> {
            return GenericDataResult(err, msg, null)
        }
    }
}