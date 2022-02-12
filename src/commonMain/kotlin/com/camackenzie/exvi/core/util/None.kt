package com.camackenzie.exvi.core.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@kotlinx.serialization.Serializable
class None : SelfSerializable {

    companion object {
        val instance: None = None()
    }

    private constructor()

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return "None"
    }
}