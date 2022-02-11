/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.model.Exercise
import com.camackenzie.exvi.core.model.ExerciseSet

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class ActiveExercise(val exercise: Exercise, val targetSets: ExerciseSet, var activeSets: ExerciseSet) {

    constructor(ex: ExerciseSet) : this(
        ex.exercise,
        ex,
        ExerciseSet(ex.exercise, ex.unit, Array<Int>(ex.sets.size) { ex.sets[it] })
    ) {
    }
}