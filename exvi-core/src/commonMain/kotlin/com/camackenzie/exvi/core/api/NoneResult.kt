package com.camackenzie.exvi.core.api

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@kotlinx.serialization.Serializable
object NoneResult : GenericDataResult("NoneResult") {

    @kotlin.jvm.Transient
    @kotlinx.serialization.Transient
    const val uid = "NoneResult"

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid
}