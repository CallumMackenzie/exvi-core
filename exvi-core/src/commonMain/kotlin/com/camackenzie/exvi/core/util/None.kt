package com.camackenzie.exvi.core.util

import com.camackenzie.exvi.core.model.ExviSerializer

@kotlinx.serialization.Serializable
@Suppress("unused")
object None : SelfSerializable {
    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid
    const val uid = "None"
}