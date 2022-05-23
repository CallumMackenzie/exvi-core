/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActualActiveWorkout
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.Serializable

/**
 * Response type: NoneResult
 */
@Serializable
@Suppress("unused")
data class ActiveWorkoutPutRequest(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache,
    val workouts: Array<ActualActiveWorkout>
) : GenericDataRequest(), ValidatedUserRequest {
    constructor(username: String, accessKey: String, workouts: Array<ActualActiveWorkout>) : this(
        username.cached(),
        accessKey.cached(),
        workouts
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ActiveWorkoutPutRequest
        if (username != other.username) return false
        if (accessKey != other.accessKey) return false
        if (!workouts.contentEquals(other.workouts)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + accessKey.hashCode()
        result = 31 * result + workouts.contentHashCode()
        return result
    }
}