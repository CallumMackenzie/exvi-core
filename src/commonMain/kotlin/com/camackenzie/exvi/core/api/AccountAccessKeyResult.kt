/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache

/**
 *
 * @author callum
 */
class AccountAccessKeyResult(
    error: Int,
    message: String?,
    accessKey: String?
) : com.camackenzie.exvi.core.api.DataResult<EncodedStringCache?>(
    error, message,
    EncodedStringCache(accessKey)
) {
    constructor(msg: String?, key: String?) : this(0, msg, key) {}
    constructor(err: Int, msg: String?) : this(err, msg, "") {}

    val accessKey: String
        get() = this.getResult().get()
}