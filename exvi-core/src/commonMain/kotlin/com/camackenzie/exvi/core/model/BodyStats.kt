/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

@Suppress("unused")
interface BodyStats : SelfSerializable {
    var sex: GeneticSex
    var totalMass: Mass
    var height: Distance

    fun toActual() = ActualBodyStats(sex, totalMass, height)

    companion object {
        /**
         * Constructs a new ActualBodyStats object
         */
        @JvmStatic
        operator fun invoke(
            sex: GeneticSex,
            totalMass: Mass,
            height: Distance
        ) = ActualBodyStats(sex, totalMass, height)
    }
}

@Serializable
@Suppress("unused")
data class ActualBodyStats(
    override var sex: GeneticSex,
    override var totalMass: Mass,
    override var height: Distance
) : BodyStats {

    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "ActualBodyStats"

        @JvmStatic
        fun averageMale() = BodyStats(
            GeneticSex.Male,
            UnitValue(MassUnit.Pound, 190.0),
            UnitValue(DistanceUnit.Meter, 1.7)
        )

        @JvmStatic
        fun averageFemale() = BodyStats(
            GeneticSex.Female,
            UnitValue(MassUnit.Pound, 170.0),
            UnitValue(DistanceUnit.Meter, 1.625)
        )

        @JvmStatic
        fun average() = BodyStats(
            GeneticSex.Unspecified,
            UnitValue(MassUnit.Pound, 180.0),
            UnitValue(DistanceUnit.Meter, 1.68)
        )
    }

}