/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlin.collections.ArrayList
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

@Suppress("unused")
interface Workout : SelfSerializable, Identifiable {
    var name: String
    var description: String
    val exercises: ArrayList<ExerciseSet>
    val id: EncodedStringCache

    fun newActiveWorkout(): ActiveWorkout

    override fun getIdentifier(): EncodedStringCache = id

    fun formatToTable(): String {
        // Retrieve the longest exercise name
        var longestExerciseName = 0
        for (exercise in exercises) {
            val exerciseName = exercise.exercise.name
            if (exerciseName.length > longestExerciseName) {
                longestExerciseName = exerciseName.length
            }
        }

        // Retrieve the longest string rep representation
        var longestRepAmount = 0
        for ((_, unit, sets) in exercises) {
            for (set in sets) {
                val setString = "$set $unit"
                if (setString.length > longestRepAmount) {
                    longestRepAmount = setString.length
                }
            }
        }

        // Build formatted table
        val ret = StringBuilder()
        for ((exercise1, unit, sets) in exercises) {
            // Get the number of spaces needed to fill the largest space
            val exerciseName = exercise1.name
            val numNameFillerSpaces = longestExerciseName - exerciseName.length
            // Append the beginning of the formatted row
            ret.append("| ")
                .append(exerciseName)
                .append(" ".repeat(numNameFillerSpaces))
                .append(" | ")
            // Fill the rest of the table according to exercise set count/reps
            for (set in sets) {
                // Get the number of spaces needed to fill the largest space
                val setString = "$set $unit"
                val numSetFillerSpaces = longestRepAmount - setString.length
                // Append the formatted set row
                ret.append("| ")
                    .append(setString)
                    .append(" ".repeat(numSetFillerSpaces))
                    .append(" ")
            }
            ret.append("\n")
        }

        // Return formatted string
        return ret.toString()
    }

    companion object {
        /**
         * Constructs a new ActualWorkout
         */
        @JvmStatic
        operator fun invoke(
            name: String = "",
            description: String = "",
            exercises: ArrayList<ExerciseSet> = arrayListOf(),
            id: EncodedStringCache
        ) = ActualWorkout(name, description, exercises, id)

        /**
         * Constructs a new ActualWorkout
         */
        @JvmStatic
        operator fun invoke(
            name: String = "",
            description: String = "",
            exercises: ArrayList<ExerciseSet> = arrayListOf()
        ) = invoke(name, description, exercises, Identifiable.generateId())

        /**
         * Constructs a new ActualWorkout
         */
        @JvmStatic
        operator fun invoke(
            other: Workout
        ) = invoke(other.name, other.description, other.exercises, other.id)
    }
}

@Serializable
@Suppress("unused")
data class ActualWorkout(
    override var name: String = "",
    override var description: String = "",
    override val exercises: ArrayList<ExerciseSet> = arrayListOf(),
    override val id: EncodedStringCache
) : Workout {
    override fun newActiveWorkout(): ActiveWorkout = ActiveWorkout(this)
    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "ActualWorkout"
    }
}