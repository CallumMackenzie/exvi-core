/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

typealias Mass = UnitValue<MassUnit>

val Number.kilograms
    get() = Mass(MassUnit.Kilogram, toDouble())
val Number.pounds
    get() = Mass(MassUnit.Pound, toDouble())
val Number.grams
    get() = Mass(MassUnit.Gram, toDouble())

val Mass.kilograms get() = toUnit(MassUnit.Kilogram)
val Mass.pounds get() = toUnit(MassUnit.Pound)
val Mass.grams get() = toUnit(MassUnit.Gram)

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
enum class MassUnit(private val unit: Double) : Unit {
    Kilogram(1.0),
    Pound(2.20462),
    Gram(1000.0);

    override fun getBaseCoefficient(): Double = unit

    companion object {
        fun none() = Mass(Kilogram, 0.0)
    }
}