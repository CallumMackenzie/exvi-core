/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class Exercise(
    var name: String,
    var description: String,
    var videoLink: String,
    var tips: String,
    var overview: String,
    var musclesWorked: Array<MuscleWorkData>,
    var exerciseTypes: HashSet<ExerciseType>,
    var experienceLevel: ExerciseExperienceLevel,
    var mechanics: ExerciseMechanics,
    var forceType: ExerciseForceType,
    var equipment: HashSet<ExerciseEquipment>
) : Comparable<Exercise> {

    fun worksMuscle(m: Muscle): Boolean {
        for ((muscle) in musclesWorked) {
            if (muscle.isInvolvedIn(m!!)) {
                return true
            }
        }
        return false
    }

    override fun compareTo(e: Exercise): Int {
        return name.compareTo(e.name)
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Exercise) {
            this.compareTo(other) == 0
        } else false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}