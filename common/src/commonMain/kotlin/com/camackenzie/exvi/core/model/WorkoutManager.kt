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
data class WorkoutManager constructor(val workouts: ArrayList<Workout>, val activeWorkouts: ArrayList<ActiveWorkout>) {

    fun getNamedWorkout(name: String?): Workout? {
        for (w in workouts) {
            if (w.name.equals(name, ignoreCase = true)) {
                return w
            }
        }
        return null
    }
}