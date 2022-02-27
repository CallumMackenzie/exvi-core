/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

typealias Mass = UnitValue<MassUnit>

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
}