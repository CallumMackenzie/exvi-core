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

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
open class GenericDataResult<T : SelfSerializable> : DataResult<T> {
    val responder: EncodedStringCache
    override val result: T?

    constructor(err: Int, msg: String, resp: T) : super(err, msg) {
        responder = resp.getUID().cached()
        result = resp
    }

    constructor(resp: T) : this(0, "Success", resp) {}

    companion object {
        fun <T : SelfSerializable> success(resp: T): GenericDataResult<T> {
            return GenericDataResult(resp)
        }

        fun faliure(msg: String, err: Int): GenericDataResult<None> {
            return GenericDataResult(err, msg, None.instance)
        }
    }
}