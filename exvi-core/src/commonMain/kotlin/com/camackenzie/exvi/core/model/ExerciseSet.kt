/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.Polymorphic
import kotlin.jvm.JvmStatic

@Suppress("unused")
interface ExerciseSet : SelfSerializable {
    @Polymorphic
    var exercise: Exercise
    var unit: String

    @Polymorphic
    val sets: MutableList<SingleExerciseSet>

    operator fun component1(): Exercise = exercise
    operator fun component2(): String = unit
    operator fun component3(): MutableList<SingleExerciseSet> = sets

    // Converts the inner exercise to standard of same name if present
    // Returns whether the exercise was standardized or not
    fun tryStandardize(): Boolean {
        val standard = exercise.tryStandardize()
        return if (standard != null) {
            exercise = standard
            true
        } else false
    }

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

