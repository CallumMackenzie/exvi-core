package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ExviSerializer
import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
@Suppress("unused")
object NoneResult : GenericDataResult("NoneResult") {

    @kotlin.jvm.Transient
    @Transient
    const val uid = "NoneResult"

    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid
}