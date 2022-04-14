/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
@Suppress("unused")
abstract class GenericDataRequest : SelfSerializable<GenericDataRequest> {
    override val serializer: KSerializer<GenericDataRequest>
        get() = Companion.serializer()
}

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
abstract class GenericDataResult : SelfSerializable<GenericDataResult> {
    override val serializer: KSerializer<GenericDataResult>
        get() = Companion.serializer()
}