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
data class BodyStats(
    var sex: GeneticSex,
    var totalMass: Mass,
    var height: Distance
) : SelfSerializable {

    override fun toJson(): String = Json.encodeToString(this)

    override fun getUID(): String = uid

    companion object {
        const val uid = "BodyStats"

        @kotlin.jvm.JvmStatic
        fun averageMale(): BodyStats {
            return BodyStats(
                GeneticSex.Male,
                UnitValue(MassUnit.Pound, 190.0),
                UnitValue(DistanceUnit.Meter, 1.7)
            )
        }

        @kotlin.jvm.JvmStatic
        fun averageFemale(): BodyStats {
            return BodyStats(
                GeneticSex.Female,
                UnitValue(MassUnit.Pound, 170.0),
                UnitValue(DistanceUnit.Meter, 1.625)
            )
        }

        @kotlin.jvm.JvmStatic
        fun average(): BodyStats {
            return BodyStats(
                GeneticSex.Unspecified,
                UnitValue(MassUnit.Pound, 180.0),
                UnitValue(DistanceUnit.Meter, 1.68)
            )
        }
    }

}