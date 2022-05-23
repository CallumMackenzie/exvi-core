/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlinx.serialization.Serializable

@Serializable
data class FriendedUser(
    val username: EncodedStringCache,
    val acceptedRequest: Boolean,
)