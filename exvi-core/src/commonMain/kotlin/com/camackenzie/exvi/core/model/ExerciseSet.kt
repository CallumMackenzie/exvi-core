/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class ExerciseSet(
    val exercise: Exercise,
    var unit: String,
    var sets: Array<SingleExerciseSet>
) : SelfSerializable {

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

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    companion object {
        const val uid = "ExerciseSet"

        @kotlin.jvm.JvmStatic
        fun repSets(ex: Exercise, sets: Array<SingleExerciseSet>): ExerciseSet {
            return ExerciseSet(ex, "rep", sets)
        }

        @kotlin.jvm.JvmStatic
        fun secondSets(ex: Exercise, sets: Array<SingleExerciseSet>): ExerciseSet {
            return ExerciseSet(ex, "second", sets)
        }

        @kotlin.jvm.JvmStatic
        fun minuteSets(ex: Exercise, sets: Array<SingleExerciseSet>): ExerciseSet {
            return ExerciseSet(ex, "minute", sets)
        }
    }
}