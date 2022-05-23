/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.datetime.Clock
import kotlinx.serialization.Polymorphic
import kotlin.jvm.JvmStatic


@Suppress("unused")
interface ActiveWorkout : SelfSerializable, Identifiable {
    val name: String
    val baseWorkoutId: EncodedStringCache

    @Polymorphic
    var exercises: Array<ActiveExercise>
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
    }.toTypedArray(), activeWorkoutId.copy(), startTimeMillis, endTimeMillis)

    // Converts all non-standard exercises within to equivalent standard ones with the same name
    // Returns indices of exercises standardized
    fun tryStandardize(): Array<Int> {
        val nStandardized = ArrayList<Int>(exercises.size)
        for ((idx, exSet) in exercises.iterator().withIndex())
            if (exSet.tryStandardize()) nStandardized.add(idx)
        return nStandardized.toTypedArray()
    }

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

