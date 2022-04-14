/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlin.math.abs

/**
 *
 * @author callum
 */
@Suppress("unused", "UNCHECKED_CAST")
@Serializable
data class MuscleWorkData(val muscle: Muscle, val workCoefficient: Double) : SelfSerializable {

    override fun equals(other: Any?): Boolean = if (other is MuscleWorkData)
        equals(other, 0.1)
    else false

    fun equals(other: MuscleWorkData, coefficientTolerance: Double): Boolean =
        other.muscle.isInvolvedIn(this.muscle) &&
                abs(other.workCoefficient - this.workCoefficient) < coefficientTolerance

    override fun hashCode(): Int {
        var result = muscle.hashCode()
        result = 31 * result + workCoefficient.hashCode()
        return result
    }

    override val serializer: KSerializer<SelfSerializable>
        get() = Companion.serializer() as KSerializer<SelfSerializable>

}