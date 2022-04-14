/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ExviSerializer
import kotlinx.serialization.json.*
import kotlinx.serialization.*

@Serializable

@Suppress("unused")
data class CompatibleVersionRequest(
    val version: Int
) : GenericDataRequest(uid) {
    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "CompatibleVersionRequest"
    }
}

@Serializable
@Suppress("unused")
data class BooleanResult(val result: Boolean) : GenericDataResult(uid) {
    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "BooleanResult"
    }
}