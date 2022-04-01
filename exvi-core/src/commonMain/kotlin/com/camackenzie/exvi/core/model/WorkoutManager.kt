/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.APIResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.Unit

@Suppress("unused")
interface WorkoutManager {
    fun getWorkouts(
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

    fun getActiveWorkouts(
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
