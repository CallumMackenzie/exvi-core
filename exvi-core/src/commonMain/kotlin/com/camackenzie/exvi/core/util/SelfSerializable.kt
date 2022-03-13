package com.camackenzie.exvi.core.util

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Suppress("unused")
interface SelfSerializable {
    fun toJson(): String
    fun getUID(): String

    val identifiedMap: Map<String, String>
        get() = mapOf(getUID() to toJson())

    val identifiedMapJson
        get() = Json.encodeToString(identifiedMap)
}