/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.datetime.Clock
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class ActiveWorkout(
    val name: String,
    val workoutId: EncodedStringCache,
    val exercises: Array<ActiveExercise>,
    private var startTimeMillis: Long? = null,
    private var endTimeMillis: Long? = null
) : SelfSerializable {

    val startTime: Time?
        get() = if (hasStarted())
            Time(TimeUnit.Millisecond, startTimeMillis!!.toDouble())
        else null
    val endTime: Time?
        get() = if (hasEnded())
            Time(TimeUnit.Millisecond, startTimeMillis!!.toDouble())
        else null

    constructor(workout: Workout) : this(
        workout.name,
        workout.id.copy(),
        workout.exercises.map { exercise ->
            ActiveExercise(exercise)
        }.toTypedArray()
    )

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
        Time(TimeUnit.Millisecond, finalElapsedTimeMillis()!!.toDouble())
    else null

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ActiveWorkout

        if (!exercises.contentEquals(other.exercises)) return false
        if (name != other.name) return false
        if (workoutId != other.workoutId) return false
        if (startTimeMillis != other.startTimeMillis) return false
        if (endTimeMillis != other.endTimeMillis) return false

        return true
    }

    override fun hashCode(): Int {
        var result = exercises.contentHashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + workoutId.hashCode()
        result = 31 * result + (startTimeMillis?.hashCode() ?: 0)
        result = 31 * result + (endTimeMillis?.hashCode() ?: 0)
        return result
    }

    companion object {
        const val uid = "ActiveWorkout"
    }
}