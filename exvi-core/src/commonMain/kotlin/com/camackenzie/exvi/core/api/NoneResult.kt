package com.camackenzie.exvi.core.api

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@kotlinx.serialization.Serializable
class NoneResult : GenericDataResult(uid) {

    companion object {
        @kotlin.jvm.JvmStatic
        val uid = "NoneResult"
    }

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }
}