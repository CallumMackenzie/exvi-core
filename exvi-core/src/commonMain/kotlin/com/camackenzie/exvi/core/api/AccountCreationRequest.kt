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
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class AccountCreationRequest(
    val username: EncodedStringCache,
    val verificationCode: EncodedStringCache,
    val password: EncodedStringCache // This is the password hash, not the raw password
) : GenericDataRequest() {
    constructor(
        username: String,
        verificationCode: String,
        password: String
    ) : this(username.cached(), verificationCode.cached(), password.cached())
}