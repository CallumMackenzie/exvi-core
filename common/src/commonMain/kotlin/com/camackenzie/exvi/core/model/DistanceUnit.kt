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
enum class DistanceUnit(private val unit: Double) : Unit {
    CENTIMETER(1.0), METER(0.01), INCH(0.39370078740109), FOOT(0.0328084);

    override fun getBaseCoefficient(): Double {
        return unit
    }
}