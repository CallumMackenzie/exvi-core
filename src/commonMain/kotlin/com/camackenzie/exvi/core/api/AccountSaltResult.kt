/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class AccountSaltResult : DataResult<EncodedStringCache> {
    constructor(
        err: Int,
        msg: String, salt: String
    ) : super(err, msg, salt.cached()) {
    }

    constructor(err: Int, msg: String) : super(err, msg, "".cached()) {}
    constructor(msg: String, salt: String) : super(0, msg, salt.cached()) {}

    val salt: String
        get() = result!!.get()
}