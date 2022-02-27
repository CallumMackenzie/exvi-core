/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.datetime.Clock
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class ActiveWorkout : SelfSerializable {
    val exercises: Array<ActiveExercise>
    val name: String
    var startTimeMillis: Long?
        private set
    var endTimeMillis: Long?
        private set

    val startTime: Time?
        get() = Time(TimeUnit.Millisecond, startTimeMillis as Double)
    val endTime: Time?
        get() = Time(TimeUnit.Millisecond, startTimeMillis as Double)

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
        return hasStarted() && endTimeMillis != null
    }

    fun finalElapsedTimeMillis(): Long? =
        if (hasEnded()) endTimeMillis!! - startTimeMillis!!
        else null

    fun finalElapsedTime(): Time? = if (hasEnded())
        Time(TimeUnit.Millisecond, finalElapsedTimeMillis() as Double)
    else null

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    companion object {
        const val uid = "ActiveWorkout"
    }
}