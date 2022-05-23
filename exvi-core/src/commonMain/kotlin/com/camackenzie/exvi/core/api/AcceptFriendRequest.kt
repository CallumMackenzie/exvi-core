/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlinx.serialization.Serializable

/**
 * Response type: NoneResult
 */
@Serializable
@Suppress("unused")
data class AcceptFriendRequest(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache,
    val toAccept: Array<EncodedStringCache>,
) : GenericDataRequest(), ValidatedUserRequest {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AcceptFriendRequest

        if (username != other.username) return false
        if (accessKey != other.accessKey) return false
        if (!toAccept.contentEquals(other.toAccept)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + accessKey.hashCode()
        result = 31 * result + toAccept.contentHashCode()
        return result
    }
}