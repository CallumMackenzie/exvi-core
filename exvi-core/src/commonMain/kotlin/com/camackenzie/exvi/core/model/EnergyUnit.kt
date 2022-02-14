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
enum class EnergyUnit(private val unit: Double) : Unit {
    KILOJOULE(1.0), KILOCALORIE(0.239006);

    override fun getBaseCoefficient(): Double {
        return unit
    }
}