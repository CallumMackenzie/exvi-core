/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class BodyStats(
    var sex: GeneticSex,
    var totalMass: UnitValue<MassUnit>,
    var height: UnitValue<DistanceUnit>
) {
    companion object {
        fun averageMale(): BodyStats {
            return BodyStats(
                GeneticSex.MALE,
                UnitValue(MassUnit.POUND, 190.0),
                UnitValue(DistanceUnit.METER, 1.7)
            )
        }

        fun averageFemale(): BodyStats {
            return BodyStats(
                GeneticSex.FEMALE,
                UnitValue(MassUnit.POUND, 170.0),
                UnitValue(DistanceUnit.METER, 1.625)
            )
        }

        fun average(): BodyStats {
            return BodyStats(
                GeneticSex.UNKNOWN,
                UnitValue(MassUnit.POUND, 180.0),
                UnitValue(DistanceUnit.METER, 1.68)
            )
        }
    }
}