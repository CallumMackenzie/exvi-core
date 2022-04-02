/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

fun ExerciseSet.toActual() = ActualExerciseSet(exercise, unit, sets.map {
    it.toActual()
}.toTypedArray())

@Suppress("unused")
interface ExerciseSet : SelfSerializable {
    operator fun component1(): Exercise = exercise
    operator fun component2(): String = unit
    operator fun component3(): Array<SingleExerciseSet> = sets

    val exercise: Exercise
    var unit: String
    var sets: Array<SingleExerciseSet>

    companion object {
        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        operator fun invoke(
            exercise: Exercise,
            unit: String,
            sets: Array<SingleExerciseSet>
        ) = ActualExerciseSet(exercise, unit, sets)

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        operator fun invoke(
            exercise: Exercise, unit: String, sets: Array<Int>
        ) = invoke(exercise, unit, sets.map { SingleExerciseSet(it) }.toTypedArray())

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        fun repSets(ex: Exercise, sets: Array<SingleExerciseSet>) = ExerciseSet(ex, "rep", sets)

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        fun secondSets(ex: Exercise, sets: Array<SingleExerciseSet>) = ExerciseSet(ex, "second", sets)

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        fun minuteSets(ex: Exercise, sets: Array<SingleExerciseSet>) = ExerciseSet(ex, "minute", sets)
    }
}

@Serializable
@Suppress("unused")
data class ActualExerciseSet(
    override val exercise: Exercise,
    override var unit: String,
    override var sets: Array<SingleExerciseSet>
) : ExerciseSet {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ExerciseSet

        if (exercise != other.exercise) return false
        if (unit != other.unit) return false
        if (!sets.contentEquals(other.sets)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = exercise.hashCode()
        result = 31 * result + unit.hashCode()
        result = 31 * result + sets.contentHashCode()
        return result
    }

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    companion object {
        const val uid = "ActualExerciseSet"
    }
}