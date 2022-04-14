/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.Serializable


/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class AccountAccessKeyResult(
    val result: EncodedStringCache
) : GenericDataResult() {
    val accessKey: String
        get() = this.result.get()

    constructor(result: String) : this(result.cached())
}