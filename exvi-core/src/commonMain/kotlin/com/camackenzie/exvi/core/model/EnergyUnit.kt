/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

typealias Energy = UnitValue<EnergyUnit>

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
@Suppress("unused")
enum class EnergyUnit(private val unit: Double) : Unit {
    KiloJoule(1.0),
    KiloCalorie(0.239006);

    override fun getBaseCoefficient(): Double = unit

    companion object {
        fun none() : Energy = Energy(KiloJoule, 0.0)
    }
}