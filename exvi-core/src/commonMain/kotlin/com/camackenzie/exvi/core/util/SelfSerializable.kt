package com.camackenzie.exvi.core.util

import com.camackenzie.exvi.core.model.ExviSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Transient

interface SelfSerializable {
    fun toJson(): String = ExviSerializer.toJson(serializer, this)
    fun toJsonElement() = ExviSerializer.toJsonElement(serializer, this)

    @Transient
    val serializer: KSerializer<SelfSerializable>
}