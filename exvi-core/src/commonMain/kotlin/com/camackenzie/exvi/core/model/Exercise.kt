/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal

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

    fun tryStandardize(): Exercise? = if (StandardExercise.standardExercisesContains(this.name))
        StandardExercise(this.name)
    else null

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
        ) = ActualExercise(
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
        get() = Companion.serializer() as KSerializer<SelfSerializable>

}

private object StandardExerciseSerializer : KSerializer<StandardExercise> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("EXVI_STDEXC") {
        element<String>("cn")
    }

    override fun serialize(encoder: Encoder, value: StandardExercise) {
        val struct = encoder.beginStructure(descriptor)
        struct.encodeStringElement(descriptor, 0, value.name)
        struct.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): StandardExercise {
        val struct = decoder.beginStructure(descriptor)
        var cachedName: String? = null
        SerializerLoop@ while (true) {
            when (val index = struct.decodeElementIndex(descriptor)) {
                0 -> {
                    cachedName = struct.decodeStringElement(descriptor, index)
                    break
                }
                else -> break@SerializerLoop
            }
        }
        struct.endStructure(descriptor)
        return if (cachedName == null) throw SerializationException("No cached name found in JSON")
        else StandardExercise(cachedName)
    }
}

@Serializable(with = StandardExerciseSerializer::class)
@Suppress("unused", "UNCHECKED_CAST")
data class StandardExercise(
    override var name: String,
) : Exercise by standardExerciseSet?.get(name) ?: placeholderBase {

    override fun equals(other: Any?): Boolean = if (other is Exercise) {
        this.name == other.name
    } else false

    // Exercise is already standard
    override fun tryStandardize(): Exercise? = null

    override fun hashCode(): Int = name.hashCode()
    override val serializer: KSerializer<SelfSerializable>
        get() = StandardExerciseSerializer as KSerializer<SelfSerializable>

    fun hasPlaceholderBase(): Boolean = this.videoLink == placeholderBase.videoLink
            && this.description == placeholderBase.description
            && this.equipment == placeholderBase.equipment

    @ThreadLocal
    companion object {

        private val placeholderBase: Exercise = ActualExercise(
            "PLACEHOLDER",
            description = "PLACEHOLDER_BASE",
            videoLink = "NONE",
            musclesWorked = emptyArray(),
            exerciseTypes = HashSet(),
            experienceLevel = ExerciseExperienceLevel.Beginner,
            mechanics = ExerciseMechanics.Other,
            forceType = ExerciseForceType.Other,
            equipment = HashSet()
        )

        var standardExerciseSet: Map<String, ActualExercise>? = null
            private set

        fun standardExercisesContains(name: String) = standardExerciseSet?.containsKey(name) ?: false

        fun setStandardExerciseSet(exs: Array<ActualExercise>) {
            val map = HashMap<String, ActualExercise>(exs.size)
            for (item in exs) map[item.name] = item
            standardExerciseSet = map
        }

    }
}