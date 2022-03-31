/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

interface ExerciseSet : SelfSerializable {
    val exercise: Exercise
    var unit: String
    var sets: Array<SingleExerciseSet>

    companion object {
        @kotlin.jvm.JvmStatic
        operator fun invoke(
            exercise: Exercise,
            unit: String,
            sets: Array<SingleExerciseSet>
        ): ExerciseSet = ActualExerciseSet(exercise, unit, sets)

        @kotlin.jvm.JvmStatic
        fun repSets(ex: Exercise, sets: Array<SingleExerciseSet>): ExerciseSet = ExerciseSet(ex, "rep", sets)

        @kotlin.jvm.JvmStatic
        fun secondSets(ex: Exercise, sets: Array<SingleExerciseSet>): ExerciseSet = ExerciseSet(ex, "second", sets)

        @kotlin.jvm.JvmStatic
        fun minuteSets(ex: Exercise, sets: Array<SingleExerciseSet>): ExerciseSet = ExerciseSet(ex, "minute", sets)
    }
}

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class ActualExerciseSet(
    override val exercise: Exercise,
    override var unit: String,
    override var sets: Array<SingleExerciseSet>
) : ExerciseSet {

    constructor(exercise: Exercise, unit: String, sets: Array<Int>)
            : this(exercise, unit, sets.map { SingleExerciseSet(it) }.toTypedArray())

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