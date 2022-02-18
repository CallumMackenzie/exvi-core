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
data class BodyStats(
    var sex: GeneticSex,
    var totalMass: UnitValue<MassUnit>,
    var height: UnitValue<DistanceUnit>
) : SelfSerializable {

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return uid
    }

    companion object {
        const val uid = "BodyStats"

        @kotlin.jvm.JvmStatic
        fun averageMale(): BodyStats {
            return BodyStats(
                GeneticSex.MALE,
                UnitValue(MassUnit.POUND, 190.0),
                UnitValue(DistanceUnit.METER, 1.7)
            )
        }

        @kotlin.jvm.JvmStatic
        fun averageFemale(): BodyStats {
            return BodyStats(
                GeneticSex.FEMALE,
                UnitValue(MassUnit.POUND, 170.0),
                UnitValue(DistanceUnit.METER, 1.625)
            )
        }

        @kotlin.jvm.JvmStatic
        fun average(): BodyStats {
            return BodyStats(
                GeneticSex.UNKNOWN,
                UnitValue(MassUnit.POUND, 180.0),
                UnitValue(DistanceUnit.METER, 1.68)
            )
        }
    }

}