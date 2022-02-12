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
class LoginRequest {
    private val username: EncodedStringCache
    private val passwordHash: EncodedStringCache

    constructor(username: String, passwordHash: String) {
        this.username = username.cached()
        this.passwordHash = passwordHash.cached()
    }
}