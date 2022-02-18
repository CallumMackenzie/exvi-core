package com.camackenzie.exvi.core.api

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@kotlinx.serialization.Serializable
object NoneResult : GenericDataResult("NoneResult") {

    @kotlin.jvm.JvmField
    @kotlin.jvm.Transient
    @kotlinx.serialization.Transient
    val uid = "NoneResult"

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }
}