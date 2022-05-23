/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlinx.serialization.Serializable

/**
 * Response type: RemoteWorkoutResponse
 */
@Serializable
@Suppress("unused")
data class GetFriendWorkouts(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache,
    val friend: EncodedStringCache,
) : GenericDataRequest(), ValidatedUserRequest