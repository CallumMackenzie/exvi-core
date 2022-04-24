/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.datetime.Clock
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic


@Suppress("unused")
interface ActiveWorkout : SelfSerializable, Identifiable {
    val name: String
    val baseWorkoutId: EncodedStringCache

    @Polymorphic
    var exercises: List<ActiveExercise>
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

    fun toActual() = ActualActiveWorkout(name, baseWorkoutId.copy(), exercises.map {
        it.toActual()
    }, activeWorkoutId.copy(), startTimeMillis, endTimeMillis)

    companion object {
        /**
         * Constructs a new ActualActiveWorkout object
         */
        @JvmStatic
        operator fun invoke(
            name: String,
            baseWorkoutId: EncodedStringCache,
            exercises: List<ActiveExercise>,
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
            }
        )
    }
}

@Serializable
@Suppress("unused", "UNCHECKED_CAST")
data class ActualActiveWorkout(
    override val name: String,
    override val baseWorkoutId: EncodedStringCache,
    override var exercises: List<ActiveExercise>,
    override val activeWorkoutId: EncodedStringCache = Identifiable.generateId(),
    override var startTimeMillis: Long? = null,
    override var endTimeMillis: Long? = null
) : ActiveWorkout {
    override val serializer: KSerializer<SelfSerializable>
        get() = Companion.serializer() as KSerializer<SelfSerializable>

    /**
     * Auto generated
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ActiveWorkout

        if (name != other.name) return false
        if (baseWorkoutId != other.baseWorkoutId) return false
        if (exercises != other.exercises) return false
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
        result = 31 * result + exercises.hashCode()
        result = 31 * result + activeWorkoutId.hashCode()
        result = 31 * result + (startTimeMillis?.hashCode() ?: 0)
        result = 31 * result + (endTimeMillis?.hashCode() ?: 0)
        return result
    }
}