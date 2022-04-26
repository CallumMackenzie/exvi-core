/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.APIResult
import com.camackenzie.exvi.core.api.WorkoutListRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Suppress("unused")
interface WorkoutManager {
    // Type must have workoutType of WorkoutType.Template
    fun getWorkouts(
        type: WorkoutListRequest.Type,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: (Array<Workout>) -> Unit = {},
        onComplete: () -> Unit = {}
    ): Job

    fun putWorkouts(
        workoutsToAdd: Array<Workout>,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    ): Job

    fun deleteWorkouts(
        toDelete: Array<String>,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    ): Job

    // Type must have workoutType of WorkoutType.Active
    fun getActiveWorkouts(
        type: WorkoutListRequest.Type,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: (Array<ActiveWorkout>) -> Unit = {},
        onComplete: () -> Unit = {}
    ): Job

    fun putActiveWorkouts(
        workoutsToAdd: Array<ActiveWorkout>,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    ): Job

    fun deleteActiveWorkouts(
        toDelete: Array<String>,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    ): Job

}
