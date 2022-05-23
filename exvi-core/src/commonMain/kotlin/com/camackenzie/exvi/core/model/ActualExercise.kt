/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused", "UNCHECKED_CAST")
data class ActualExercise(
    override var name: String,
    override var description: String = "",
    override var videoLink: String = "",
    override var tips: String = "",
    override var overview: String = "",
    override var musclesWorked: Array<MuscleWorkData>,
    override var exerciseTypes: HashSet<ExerciseType>,
    override var experienceLevel: ExerciseExperienceLevel,
    override var mechanics: ExerciseMechanics,
    override var forceType: ExerciseForceType,
    override var equipment: HashSet<ExerciseEquipment>
) : Exercise {

    override fun equals(other: Any?): Boolean = if (other is Exercise) {
        this.name == other.name
    } else false

    override fun hashCode(): Int = name.hashCode()
    override val serializer: KSerializer<SelfSerializable>
        get() = ActualExercise.serializer() as KSerializer<SelfSerializable>

}