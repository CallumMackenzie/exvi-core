/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

@Suppress("unused")
interface ActiveExercise : SelfSerializable {
    @Polymorphic
    val target: ExerciseSet

    @Polymorphic
    var active: ExerciseSet
    var currentSet: Int

    val exercise: Exercise
        get() = target.exercise

    fun toActual() = ActualActiveExercise(target, active.toActual(), currentSet)

    fun timingCallback(
        set: Int,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        callback: (Int, Time) -> Unit
    ): Job = active.sets[set].timingCallback(coroutineScope, dispatcher, callback)

    // Converts the inner exercise to standard of same name if present
    // Returns whether the exercise was standardized or not
    fun tryStandardize(): Boolean = target.tryStandardize() || active.tryStandardize()

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
                List(ex.sets.size) {
                    val ns = ex.sets[it].deepValueCopy()
                    ns.reps = 0
                    ns
                }
            ))
    }
}

@Serializable
@Suppress("unused", "UNCHECKED_CAST")
data class ActualActiveExercise(
    override val target: ExerciseSet,
    override var active: ExerciseSet,
    override var currentSet: Int = 0
) : ActiveExercise {
    override val serializer: KSerializer<SelfSerializable>
        get() = Companion.serializer() as KSerializer<SelfSerializable>
}