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
class VerificationRequest {
    private val username: EncodedStringCache
    private val email: EncodedStringCache
    private val phone: EncodedStringCache

    constructor(
        username: String,
        email: String,
        phone: String
    ) {
        this.username = username.cached()
        this.email = email.cached()
        this.phone = phone.cached()
    }
}