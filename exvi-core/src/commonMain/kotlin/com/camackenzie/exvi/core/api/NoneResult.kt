package com.camackenzie.exvi.core.api

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
@Suppress("unused")
object NoneResult : GenericDataResult("NoneResult") {

    @kotlin.jvm.Transient
    @Transient
    const val uid = "NoneResult"

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid
}