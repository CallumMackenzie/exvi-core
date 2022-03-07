/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.SelfSerializable
import com.camackenzie.exvi.core.util.cached

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
abstract class GenericDataRequest(
    val requester: EncodedStringCache
) : SelfSerializable {
    constructor(requester: String) : this(requester.cached())
}

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
abstract class GenericDataResult(
    val responder: EncodedStringCache
) : SelfSerializable {
    constructor(responder: String) : this(responder.cached())
}