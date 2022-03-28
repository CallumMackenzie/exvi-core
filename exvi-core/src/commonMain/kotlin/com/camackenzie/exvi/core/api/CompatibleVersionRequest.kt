/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import kotlinx.serialization.json.*
import kotlinx.serialization.*

@Serializable
@Suppress("unused")
class CompatibleVersionRequest : GenericDataRequest(uid) {
    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "CompatibleVersionRequest"
    }
}

@Serializable
@Suppress("unused")
data class BooleanResult(val result: Boolean) : GenericDataResult(uid) {
    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "BooleanResult"
    }
}