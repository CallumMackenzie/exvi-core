/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.APIResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.Unit

interface WorkoutManager {
    fun getWorkouts(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: (Array<Workout>) -> Unit = {},
        onComplete: () -> Unit = {}
    )

    fun putWorkouts(
        workoutsToAdd: Array<Workout>,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    )

    fun deleteWorkouts(
        toDelete: Array<String>,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        onFail: (APIResult<String>) -> Unit = {},
        onSuccess: () -> Unit = {},
        onComplete: () -> Unit = {}
    )

}
