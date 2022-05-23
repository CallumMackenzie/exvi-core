/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Suppress("UNCHECKED_CAST")
@Serializable
data class ActualSingleExerciseSet(
    override var reps: Int,
    override var weight: Mass = MassUnit.none(),
    override var timing: Array<Time> = emptyArray()
) : SingleExerciseSet {
    override val serializer: KSerializer<SelfSerializable>
        get() = ActualSingleExerciseSet.serializer() as KSerializer<SelfSerializable>

    override fun deepValueCopy(): SingleExerciseSet = ActualSingleExerciseSet(
        reps,
        weight.copy(),
        timing.map { it.copy() }.toTypedArray()
    )

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