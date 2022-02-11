/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import kotlin.collections.ArrayList

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class Workout(var name: String, var description: String, val exercises: ArrayList<ExerciseSet>) {

    fun newActiveWorkout(): ActiveWorkout {
        return ActiveWorkout(this)
    }

    fun formatToTable(): String {
        // Retrieve the longest exercise name
        var longestExerciseName = 0
        for ((exercise1) in exercises) {
            val exerciseName = exercise1.name
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