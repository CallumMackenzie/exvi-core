package com.camackenzie.exvi.core.util

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Suppress("unused")
interface SelfSerializable {
    fun toJson(): String
    fun getUID(): String

    fun toIdentifiedJson(): String = Json.encodeToString(
        mapOf(
            "uid" to getUID(),
            "value" to toJson()
        )
    )
}