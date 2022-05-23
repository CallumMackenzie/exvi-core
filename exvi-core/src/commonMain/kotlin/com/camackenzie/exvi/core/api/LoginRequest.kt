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
 * Response type: AccountAccessKeyResult
 * @author callum
 */
@Serializable
@Suppress("unused")
data class LoginRequest(
    val username: EncodedStringCache,
    val passwordHash: EncodedStringCache
) : GenericDataRequest() {
    constructor(username: String, passwordHash: String)
            : this(username.cached(), passwordHash.cached())
}