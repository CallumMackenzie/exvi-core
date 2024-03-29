@file:Suppress("unused")
/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

import kotlin.jvm.JvmStatic

typealias Energy = UnitValue<EnergyUnit>

val Number.kiloJoules
    get() = Energy(EnergyUnit.KiloJoule, toDouble())
val Number.kiloCalories
    get() = Energy(EnergyUnit.KiloCalorie, toDouble())

val Energy.kiloJoules
    get() = toUnit(EnergyUnit.KiloJoule)
val Energy.kiloCalories
    get() = toUnit(EnergyUnit.KiloCalorie)

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
@Suppress("unused")
enum class EnergyUnit(private val unit: Double) : ValueUnit {
    KiloJoule(1.0),
    KiloCalorie(0.239006);

    override fun getBaseCoefficient(): Double = unit

    companion object {
        @JvmStatic
        fun none(): Energy = Energy(KiloJoule, 0.0)
    }
}