/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActiveWorkout
import com.camackenzie.exvi.core.model.Workout
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class WorkoutListResult(val workouts: Array<Workout>) : GenericDataResult(uid) {

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    /**
     * Auto-generated
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as WorkoutListResult
        if (!workouts.contentEquals(other.workouts)) return false
        return true
    }

    override fun hashCode(): Int = workouts.contentHashCode()

    companion object {
        const val uid = "WorkoutListResult"
    }
}

@Serializable
@Suppress("unused")
data class ActiveWorkoutListResult(val workouts: Array<ActiveWorkout>) : GenericDataResult(uid) {

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    /**
     * Auto-generated
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ActiveWorkoutListResult
        if (!workouts.contentEquals(other.workouts)) return false
        return true
    }

    override fun hashCode(): Int = workouts.contentHashCode()

    companion object {
        const val uid = "ActiveWorkoutListResult"
    }

}