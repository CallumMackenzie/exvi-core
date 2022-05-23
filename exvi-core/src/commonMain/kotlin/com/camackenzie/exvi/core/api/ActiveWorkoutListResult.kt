/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActualActiveWorkout
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused")
data class ActiveWorkoutListResult(val workouts: Array<ActualActiveWorkout>) : GenericDataResult() {
    // Auto-generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ActiveWorkoutListResult
        if (!workouts.contentEquals(other.workouts)) return false
        return true
    }

    // Auto generated
    override fun hashCode(): Int = workouts.contentHashCode()
}