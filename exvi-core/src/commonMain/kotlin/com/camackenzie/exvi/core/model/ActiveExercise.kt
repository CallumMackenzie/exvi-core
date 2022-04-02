/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

@Suppress("unused")
interface ActiveExercise : SelfSerializable {
    val target: ExerciseSet
    var active: ExerciseSet
    var currentSet: Int

    val exercise: Exercise
        get() = target.exercise

    fun toActual() = ActualActiveExercise(target, active.toActual(), currentSet)

    fun timingCallback(
        set: Int,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        callback: (Int, Time) -> kotlin.Unit
    ): Job = active.sets[set].timingCallback(coroutineScope, dispatcher, callback)

    companion object {
        /**
         * Constructs a new ActualActiveExercise object
         */
        @JvmStatic
        operator fun invoke(
            target: ExerciseSet,
            active: ExerciseSet = target,
            currentSet: Int = 0
        ): ActualActiveExercise = ActualActiveExercise(target, active, currentSet)

        /**
         * Constructs a new ActualActiveExercise object
         */
        @JvmStatic
        operator fun invoke(ex: ExerciseSet): ActualActiveExercise = invoke(ex,
            ExerciseSet(
                ex.exercise,
                ex.unit,
                Array(ex.sets.size) {
                    val ns = ex.sets[it].deepValueCopy()
                    ns.reps = 0
                    ns
                }
            ))
    }
}

@Serializable
@Suppress("unused")
data class ActualActiveExercise(
    override val target: ExerciseSet,
    override var active: ExerciseSet,
    override var currentSet: Int = 0
) : ActiveExercise {
    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "ActualActiveExercise"
    }
}