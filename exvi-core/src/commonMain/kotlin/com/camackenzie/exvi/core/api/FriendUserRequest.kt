/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlinx.serialization.Serializable

/**
 * Response type: BooleanResult -> Whether the user could be friended
 */
@Serializable
@Suppress("unused")
data class FriendUserRequest(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache,
    val users: Array<EncodedStringCache>,
    val friend: Boolean,
) : GenericDataRequest(), ValidatedUserRequest {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as FriendUserRequest

        if (username != other.username) return false
        if (accessKey != other.accessKey) return false
        if (!users.contentEquals(other.users)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + accessKey.hashCode()
        result = 31 * result + users.contentHashCode()
        return result
    }
}
