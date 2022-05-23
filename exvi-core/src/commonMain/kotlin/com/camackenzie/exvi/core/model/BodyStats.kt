/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlin.jvm.JvmStatic

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

