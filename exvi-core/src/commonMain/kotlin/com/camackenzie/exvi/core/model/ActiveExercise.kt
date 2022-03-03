/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class ActiveExercise(
    val target: ExerciseSet,
    var active: ExerciseSet,
    var currentSet: Int = 0
) : SelfSerializable {

    val exercise: Exercise
        get() = target.exercise

    constructor(ex: ExerciseSet) : this(
        ex,
        ExerciseSet(
            ex.exercise,
            ex.unit,
            Array(ex.sets.size) { ex.sets[it].deepValueCopy() }
        )
    )

    fun timingCallback(
        set: Int,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        callback: (Int, Time) -> kotlin.Unit
    ): Job = active.sets[set].timingCallback(coroutineScope, dispatcher, callback)

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    companion object {
        const val uid = "ActiveExercise"
    }
}