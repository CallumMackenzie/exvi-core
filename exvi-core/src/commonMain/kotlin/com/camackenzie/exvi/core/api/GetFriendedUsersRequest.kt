/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
data class GetFriendedUsersRequest(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache
) : GenericDataRequest(), ValidatedUserRequest {
}