/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.Identifiable
import kotlinx.serialization.Serializable

@Serializable
data class FriendedUser(
    val username: EncodedStringCache,
    val acceptedRequest: Boolean,
    val incomingRequest: Boolean,
) : Identifiable {
    override fun getIdentifier(): EncodedStringCache = username
}