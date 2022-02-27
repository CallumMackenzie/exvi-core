/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class ActiveExercise(
    val exercise: Exercise,
    val targetSets: ExerciseSet,
    var activeSets: ExerciseSet
) :
    SelfSerializable {

    constructor(ex: ExerciseSet) : this(
        exercise = ex.exercise,
        targetSets = ex,
        activeSets = ExerciseSet(
            ex.exercise,
            ex.unit,
            Array(ex.sets.size) { ex.sets[it].deepValueCopy() }
        )
    )

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