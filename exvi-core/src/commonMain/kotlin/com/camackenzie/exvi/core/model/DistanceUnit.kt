/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

typealias Distance = UnitValue<DistanceUnit>

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
enum class DistanceUnit(private val unit: Double) : Unit {
    Centimeter(1.0),
    Meter(Centimeter.unit / 100.0),
    Kilometer(Meter.unit / 1000.0),
    Inch(0.39370078740109),
    Foot(Inch.unit / 12.0);

    override fun getBaseCoefficient(): Double {
        return unit
    }
}