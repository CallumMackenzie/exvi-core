/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused", "UNCHECKED_CAST")
data class ActualExerciseSet(
    override var exercise: Exercise,
    override var unit: String,
    override val sets: ArrayList<SingleExerciseSet>
) : ExerciseSet {
    override val serializer: KSerializer<SelfSerializable>
        get() = ActualExerciseSet.serializer() as KSerializer<SelfSerializable>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ExerciseSet
        if (exercise != other.exercise) return false
        if (unit != other.unit) return false
        return true
    }

    override fun hashCode(): Int {
        var result = exercise.hashCode()
        result = 31 * result + unit.hashCode()
        return result
    }
}