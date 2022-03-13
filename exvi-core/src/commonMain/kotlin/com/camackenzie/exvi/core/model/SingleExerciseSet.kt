package com.camackenzie.exvi.core.model

import kotlinx.coroutines.*

@kotlinx.serialization.Serializable
data class SingleExerciseSet(
    var reps: Int,
    var weight: Mass = MassUnit.none(),
    var timing: Array<Time> = emptyArray()
) {
    fun deepValueCopy(): SingleExerciseSet = SingleExerciseSet(
        reps,
        weight.copy(),
        timing.map { it.copy() }.toTypedArray()
    )

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
}