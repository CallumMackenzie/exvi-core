/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.datetime.Clock
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

@Suppress("unused")
interface ActiveWorkout : SelfSerializable, Identifiable {
    val name: String
    val baseWorkoutId: EncodedStringCache
    val exercises: Array<ActiveExercise>
    val activeWorkoutId: EncodedStringCache
    var startTimeMillis: Long?
    var endTimeMillis: Long?

    val startTime: Time?
        get() = if (hasStarted())
            Time(TimeUnit.Millisecond, startTimeMillis!!.toDouble())
        else null
    val endTime: Time?
        get() = if (hasEnded())
            Time(TimeUnit.Millisecond, startTimeMillis!!.toDouble())
        else null

    fun start() {
        startTimeMillis = Clock.System.now().toEpochMilliseconds()
    }

    fun end() {
        endTimeMillis = Clock.System.now().toEpochMilliseconds()
    }

    fun hasStarted(): Boolean = startTimeMillis != null

    fun hasEnded(): Boolean = hasStarted() && endTimeMillis != null

    fun finalElapsedTimeMillis(): Long? =
        if (hasEnded()) endTimeMillis!! - startTimeMillis!!
        else null

    fun finalElapsedTime(): Time? = if (hasEnded())
        Time(TimeUnit.Millisecond, finalElapsedTimeMillis()!!.toDouble())
    else null

    override fun getIdentifier(): EncodedStringCache = activeWorkoutId

    companion object {
        /**
         * Constructs a new ActualActiveWorkout object
         */
        @JvmStatic
        operator fun invoke(
            name: String,
            baseWorkoutId: EncodedStringCache,
            exercises: Array<ActiveExercise>,
            activeWorkoutId: EncodedStringCache = Identifiable.generateId(),
            startTimeMillis: Long? = null,
            endTimeMillis: Long? = null,
        ) = ActualActiveWorkout(name, baseWorkoutId, exercises, activeWorkoutId, startTimeMillis, endTimeMillis)

        /**
         * Constructs a new ActualActiveWorkout object
         */
        @JvmStatic
        operator fun invoke(workout: Workout) = invoke(
            workout.name,
            workout.id.copy(),
            workout.exercises.map { exercise ->
                ActiveExercise(exercise)
            }.toTypedArray()
        )
    }
}

@Serializable
@Suppress("unused")
data class ActualActiveWorkout(
    override val name: String,
    override val baseWorkoutId: EncodedStringCache,
    override val exercises: Array<ActiveExercise>,
    override val activeWorkoutId: EncodedStringCache = Identifiable.generateId(),
    override var startTimeMillis: Long? = null,
    override var endTimeMillis: Long? = null
) : ActiveWorkout {

    override fun toJson(): String = ExviSerializer.toJson(this)

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
        const val uid = "ActualActiveWorkout"
    }
}