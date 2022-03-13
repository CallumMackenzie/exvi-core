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
@Serializable
@Suppress("unused")
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

    fun worksMuscle(workData: MuscleWorkData): Boolean {
        for (muscle in musclesWorked) {
            if (muscle == workData) {
                return true
            }
        }
        return false
    }

    fun isType(type: ExerciseType): Boolean {
        for (ty in this.exerciseTypes) {
            if (ty == type) {
                return true
            }
        }
        return false
    }

    fun usesEquipment(equipment: ExerciseEquipment): Boolean {
        for (eq in this.equipment) {
            if (eq == equipment) {
                return true
            }
        }
        return false
    }

    fun hasDescription(): Boolean = description.isNotBlank()

    fun hasVideoLink(): Boolean = videoLink.isNotBlank()

    fun hasTips(): Boolean = tips.isNotBlank()

    fun hasOverview(): Boolean = overview.isNotBlank()

    fun hasEquipment(): Boolean = equipment.isEmpty()

    override fun compareTo(other: Exercise): Int = name.compareTo(other.name)

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    override fun equals(other: Any?): Boolean = if (other is Exercise) {
        this.name == other.name
    } else false

    override fun hashCode(): Int = name.hashCode()

    companion object {
        const val uid = "Exercise"
    }
}