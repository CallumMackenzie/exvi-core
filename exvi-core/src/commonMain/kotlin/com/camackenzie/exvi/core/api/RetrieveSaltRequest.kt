/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.Serializable

/**
 * Response type: AccountSaltResult
 * @author callum
 */
@Serializable
@Suppress("unused")
data class RetrieveSaltRequest(
    val username: EncodedStringCache
) : GenericDataRequest() {
    constructor(username: String) : this(username.cached())
}