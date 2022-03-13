package com.camackenzie.exvi.core.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
@Suppress("unused")
object None : SelfSerializable {
    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid
    const val uid = "None"
}