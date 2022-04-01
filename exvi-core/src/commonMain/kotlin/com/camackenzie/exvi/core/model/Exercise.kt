/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

@Suppress("unused")
interface Exercise : Comparable<Exercise>, SelfSerializable {
    var name: String
    var description: String
    var videoLink: String
    var tips: String
    var overview: String
    var musclesWorked: Array<MuscleWorkData>
    var exerciseTypes: HashSet<ExerciseType>
    var experienceLevel: ExerciseExperienceLevel
    var mechanics: ExerciseMechanics
    var forceType: ExerciseForceType
    var equipment: HashSet<ExerciseEquipment>

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

    companion object {
        /**
         * Constructs a new ActualExercise object
         */
        @JvmStatic
        operator fun invoke(
            name: String,
            description: String,
            videoLink: String,
            tips: String,
            overview: String,
            musclesWorked: Array<MuscleWorkData>,
            exerciseTypes: HashSet<ExerciseType>,
            experienceLevel: ExerciseExperienceLevel,
            mechanics: ExerciseMechanics,
            forceType: ExerciseForceType,
            equipment: HashSet<ExerciseEquipment>
        ): Exercise = ActualExercise(
            name,
            description,
            videoLink,
            tips,
            overview,
            musclesWorked,
            exerciseTypes,
            experienceLevel,
            mechanics,
            forceType,
            equipment,
        )
    }
}

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class ActualExercise(
    override var name: String,
    override var description: String,
    override var videoLink: String,
    override var tips: String,
    override var overview: String,
    override var musclesWorked: Array<MuscleWorkData>,
    override var exerciseTypes: HashSet<ExerciseType>,
    override var experienceLevel: ExerciseExperienceLevel,
    override var mechanics: ExerciseMechanics,
    override var forceType: ExerciseForceType,
    override var equipment: HashSet<ExerciseEquipment>
) : Exercise {
    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    override fun equals(other: Any?): Boolean = if (other is Exercise) {
        this.name == other.name
    } else false

    override fun hashCode(): Int = name.hashCode()

    companion object {
        const val uid = "ActualExercise"
    }
}