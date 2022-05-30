/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.Polymorphic
import kotlin.jvm.JvmStatic


@Suppress("unused")
interface Workout : SelfSerializable, Identifiable {
    var name: String
    var description: String
    var public: Boolean

    @Polymorphic
    val exercises: MutableList<ExerciseSet>
    val id: EncodedStringCache

    fun newActiveWorkout(): ActiveWorkout

    override fun getIdentifier(): EncodedStringCache = id

    fun toActual() = ActualWorkout(this)

    // Converts all non-standard exercises within to equivalent standard ones with the same name
    // Returns indices of exercises standardized
    fun tryStandardize(): Array<Int> {
        val nStandardized = ArrayList<Int>(exercises.size)
        for ((idx, exSet) in exercises.iterator().withIndex())
            if (exSet.tryStandardize()) nStandardized.add(idx)
        return nStandardized.toTypedArray()
    }

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
}

