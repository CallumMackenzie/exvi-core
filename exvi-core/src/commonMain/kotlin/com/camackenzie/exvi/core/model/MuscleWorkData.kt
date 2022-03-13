/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.math.abs

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class MuscleWorkData(val muscle: Muscle, val workCoefficient: Double) : SelfSerializable {

    override fun equals(other: Any?): Boolean = if (other is MuscleWorkData)
        equals(other, 0.1)
    else false

    fun equals(other: MuscleWorkData, coefficientTolerance: Double): Boolean =
        other.muscle.isInvolvedIn(this.muscle) &&
                abs(other.workCoefficient - this.workCoefficient) < coefficientTolerance

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid
    
    override fun hashCode(): Int {
        var result = muscle.hashCode()
        result = 31 * result + workCoefficient.hashCode()
        return result
    }

    companion object {
        const val uid = "MuscleWorkData"
    }

}