/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.RemoteWorkout
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
data class RemoteWorkoutResponse(
    val workouts: Array<RemoteWorkout>
) : GenericDataResult() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RemoteWorkoutResponse

        if (!workouts.contentEquals(other.workouts)) return false

        return true
    }

    override fun hashCode(): Int {
        return workouts.contentHashCode()
    }
}