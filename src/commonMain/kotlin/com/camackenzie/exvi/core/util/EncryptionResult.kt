/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util

import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
@Serializable
data class EncryptionResult(val encrypted: String, val key: CachedKey)