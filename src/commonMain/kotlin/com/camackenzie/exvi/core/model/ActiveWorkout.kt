/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import kotlinx.datetime.Clock

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class ActiveWorkout {
    val exercises: Array<ActiveExercise>
    val name: String
    var startTimeMillis: Long?
        private set
    var endTimeMillis: Long?
        private set

    constructor(workout: Workout) {
        this.name = workout.name
        this.exercises = workout.exercises.map { exercise ->
            ActiveExercise(exercise)
        }.toTypedArray()
        this.startTimeMillis = null
        this.endTimeMillis = null
    }

    fun start() {
        startTimeMillis = Clock.System.now().epochSeconds
    }

    fun end() {
        endTimeMillis = Clock.System.now().epochSeconds
    }

    fun hasStarted(): Boolean {
        return startTimeMillis != null
    }

    fun hasEnded(): Boolean {
        return endTimeMillis != null
    }

    fun finalElapsedTime(): Long? {
        return if (startTimeMillis != null && endTimeMillis != null) {
            endTimeMillis!! - startTimeMillis!!
        } else null
    }
}