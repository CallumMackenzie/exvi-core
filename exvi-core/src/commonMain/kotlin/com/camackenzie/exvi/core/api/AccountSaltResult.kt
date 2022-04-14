/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ExviSerializer
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class AccountSaltResult(val result: EncodedStringCache) : GenericDataResult(uid) {

    val salt: String
        get() = result.get()

    constructor(result: String) : this(result.cached())

    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "AccountSaltResult"
    }
}