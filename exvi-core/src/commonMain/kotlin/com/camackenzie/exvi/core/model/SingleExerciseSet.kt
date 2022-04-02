/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import kotlinx.serialization.*

fun SingleExerciseSet.toActual() = ActualSingleExerciseSet(
    reps, weight.copy(), timing.map {
        it.copy()
    }.toTypedArray()
)

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

@Serializable
data class ActualSingleExerciseSet(
    override var reps: Int,
    override var weight: Mass = MassUnit.none(),
    override var timing: Array<Time> = emptyArray()
) : SingleExerciseSet {
    override fun deepValueCopy(): SingleExerciseSet = ActualSingleExerciseSet(
        reps,
        weight.copy(),
        timing.map { it.copy() }.toTypedArray()
    )

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as SingleExerciseSet
        if (reps != other.reps) return false
        if (weight != other.weight) return false
        if (!timing.contentEquals(other.timing)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = reps
        result = 31 * result + weight.hashCode()
        result = 31 * result + timing.contentHashCode()
        return result
    }

    companion object {
        const val uid = "ActualSingleExerciseSet"
    }
}