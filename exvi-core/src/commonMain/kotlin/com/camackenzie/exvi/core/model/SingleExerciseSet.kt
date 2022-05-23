/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.coroutines.*


interface SingleExerciseSet : SelfSerializable {
    var reps: Int
    var weight: Mass
    var timing: Array<Time>

    fun deepValueCopy(): SingleExerciseSet

    fun timingCallback(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        callback: (Int, Time) -> kotlin.Unit,
    ): Job = coroutineScope.launch(dispatcher) {
        for (i in timing.indices) {
            val time = timing[i]
            delay(time.toDuration())
            callback(i, time)
        }
    }

    fun toActual() = ActualSingleExerciseSet(
        reps, weight.copy(), timing.map {
            it.copy()
        }.toTypedArray()
    )

    companion object {
        /**
         * Constructs a new ActualSingleExerciseSet object
         */
        @kotlin.jvm.JvmStatic
        operator fun invoke(
            reps: Int,
            weight: Mass = MassUnit.none(),
            timing: Array<Time> = emptyArray()
        ) = ActualSingleExerciseSet(reps, weight, timing)
    }
}

