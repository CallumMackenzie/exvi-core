package com.camackenzie.exvi.core.util

import com.camackenzie.exvi.core.model.ExviSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Transient

interface SelfSerializable<T> {
    fun toJson(): String = ExviSerializer.toJson(serializer, this as T)

    @Transient
    val serializer: KSerializer<T>
}