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
class AccountCreationRequest {
    private val username: EncodedStringCache
    private val verificationCode: EncodedStringCache
    private val password: EncodedStringCache

    constructor(
        username: String,
        verificationCode: String,
        password: String
    ) {
        this.username = username.cached()
        this.verificationCode = verificationCode.cached()
        this.password = password.cached()
    }
}