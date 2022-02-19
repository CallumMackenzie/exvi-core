/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.APIResult
import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlin.Unit

interface WorkoutManager {
    fun getWorkouts(
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: (Array<Workout>) -> Unit = {},
        onComplete: () -> Unit = {}
    )

    fun putWorkouts(
        workoutsToAdd: Array<Workout>,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    )

    fun deleteWorkouts(
        toDelete: Array<String>,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    )
}

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class LocalWorkoutManager constructor(
    val workouts: ArrayList<Workout> = ArrayList(),
    val activeWorkouts: ArrayList<ActiveWorkout> = ArrayList()
) : WorkoutManager {

    override fun getWorkouts(
        onFail: (APIResult<String>) -> Unit,
        onSuccess: (Array<Workout>) -> Unit,
        onComplete: () -> Unit
    ) {
        onSuccess(workouts.toTypedArray())
        onComplete()
    }

    override fun putWorkouts(
        workoutsToAdd: Array<Workout>,
        onFail: (APIResult<String>) -> Unit,
        onSuccess: () -> Unit,
        onComplete: () -> Unit
    ) {
        workouts.addAll(workoutsToAdd)
        onSuccess()
        onComplete()
    }

    override fun deleteWorkouts(
        toDelete: Array<String>,
        onFail: (APIResult<String>) -> Unit,
        onSuccess: () -> Unit,
        onComplete: () -> Unit
    ) {
        workouts.removeAll {
            toDelete.contains(it.id.get())
        }
        onSuccess()
        onComplete()
    }
}

