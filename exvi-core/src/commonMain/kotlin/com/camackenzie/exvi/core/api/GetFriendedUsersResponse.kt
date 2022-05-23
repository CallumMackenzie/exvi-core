/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.FriendedUser
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
data class GetFriendedUsersResponse(
    val users: Array<FriendedUser>,
) : GenericDataResult() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as GetFriendedUsersResponse

        if (!users.contentEquals(other.users)) return false

        return true
    }

    override fun hashCode(): Int {
        return users.contentHashCode()
    }
}