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
) : Comparable<Exercise>, SelfSerializable {

    fun worksMuscle(m: Muscle): Boolean {
        for ((muscle) in musclesWorked) {
            if (muscle.isInvolvedIn(m)) {
                return true
            }
        }
        return false
    }

    override fun compareTo(other: Exercise): Int {
        return name.compareTo(other.name)
    }

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Exercise) {
            this.compareTo(other) == 0
        } else false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    companion object {
        const val uid = "Exercise"
    }
}