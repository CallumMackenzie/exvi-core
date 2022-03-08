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
    val baseWorkoutId: EncodedStringCache,
    val exercises: Array<ActiveExercise>,
    val activeWorkoutId: EncodedStringCache = Workout.generateId(),
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

    fun hasStarted(): Boolean = startTimeMillis != null

    fun hasEnded(): Boolean = hasStarted() && endTimeMillis != null

    fun finalElapsedTimeMillis(): Long? =
        if (hasEnded()) endTimeMillis!! - startTimeMillis!!
        else null

    fun finalElapsedTime(): Time? = if (hasEnded())
        Time(TimeUnit.Millisecond, finalElapsedTimeMillis()!!.toDouble())
    else null

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    /**
     * Auto generated
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ActiveWorkout

        if (name != other.name) return false
        if (baseWorkoutId != other.baseWorkoutId) return false
        if (!exercises.contentEquals(other.exercises)) return false
        if (activeWorkoutId != other.activeWorkoutId) return false
        if (startTimeMillis != other.startTimeMillis) return false
        if (endTimeMillis != other.endTimeMillis) return false

        return true
    }

    /**
     * Auto generated
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + baseWorkoutId.hashCode()
        result = 31 * result + exercises.contentHashCode()
        result = 31 * result + activeWorkoutId.hashCode()
        result = 31 * result + (startTimeMillis?.hashCode() ?: 0)
        result = 31 * result + (endTimeMillis?.hashCode() ?: 0)
        return result
    }

    companion object {
        const val uid = "ActiveWorkout"
    }
}