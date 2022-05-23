/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused", "UNCHECKED_CAST")
abstract class GenericDataResult : SelfSerializable {
    override val serializer: KSerializer<SelfSerializable>
        get() = GenericDataResult.serializer() as KSerializer<SelfSerializable>
}