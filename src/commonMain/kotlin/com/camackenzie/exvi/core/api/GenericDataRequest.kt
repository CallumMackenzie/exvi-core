/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
open class GenericDataRequest<T>(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val body: T,
) {

    val requester: EncodedStringCache

    init {
        requester = body!!::class.qualifiedName!!.cached()
    }
}