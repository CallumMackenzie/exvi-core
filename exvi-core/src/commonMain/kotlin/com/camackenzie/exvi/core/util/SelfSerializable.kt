package com.camackenzie.exvi.core.util

import com.camackenzie.exvi.core.model.ExviSerializer

@Suppress("unused")
interface SelfSerializable {
    fun toJson(): String
    fun getUID(): String

    fun toIdentifiedJson(): String = ExviSerializer.toJson(
        mapOf(
            "uid" to getUID(),
            "value" to toJson()
        )
    )
}