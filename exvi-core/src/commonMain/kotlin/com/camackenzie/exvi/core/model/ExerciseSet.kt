/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

@Suppress("unused")

interface ExerciseSet : SelfSerializable {
    @Polymorphic
    val exercise: Exercise
    var unit: String

    @Polymorphic
    val sets: MutableList<SingleExerciseSet>

    operator fun component1(): Exercise = exercise
    operator fun component2(): String = unit
    operator fun component3(): MutableList<SingleExerciseSet> = sets

    fun toActual() = ActualExerciseSet(exercise, unit, ArrayList(sets.map {
        it.toActual()
    }))

    companion object {
        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        operator fun invoke(
            exercise: Exercise,
            unit: String,
            sets: List<SingleExerciseSet>
        ) = ActualExerciseSet(exercise, unit, ArrayList(sets))

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        operator fun invoke(
            exercise: Exercise, unit: String, sets: Array<Int>
        ) = invoke(exercise, unit, sets.map { SingleExerciseSet(it) }.toList())

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        fun repSets(ex: Exercise, sets: List<SingleExerciseSet>) = ExerciseSet(ex, "rep", sets)

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        fun secondSets(ex: Exercise, sets: List<SingleExerciseSet>) = ExerciseSet(ex, "second", sets)

        /**
         * Constructs a new ActualExerciseSet object
         */
        @JvmStatic
        fun minuteSets(ex: Exercise, sets: List<SingleExerciseSet>) = ExerciseSet(ex, "minute", sets)
    }
}

@Serializable

@Suppress("unused")
data class ActualExerciseSet(
    override val exercise: Exercise,
    override var unit: String,
    override val sets: ArrayList<SingleExerciseSet>
) : ExerciseSet {

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

    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "ActualExerciseSet"
    }
}