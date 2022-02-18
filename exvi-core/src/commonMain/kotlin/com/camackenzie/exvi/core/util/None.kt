package com.camackenzie.exvi.core.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@kotlinx.serialization.Serializable
object None : SelfSerializable {

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    const val uid = "None"
}