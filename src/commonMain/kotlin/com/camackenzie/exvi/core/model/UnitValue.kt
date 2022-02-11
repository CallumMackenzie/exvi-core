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
data class UnitValue<T : Unit>(val unit: T, val value: Double) {
    fun toUnit(m: T): UnitValue<T> {
        return UnitValue<T>(m, value / unit.getBaseCoefficient() * m.getBaseCoefficient())
    }

    override fun toString(): String {
        return value.toString() + unit.toString()
    }
}