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
data class UnitValue<T : Unit>(
    val unit: T,
    val value: Double
) {
    fun toUnit(unit: T): UnitValue<T> {
        return if (unit == this.unit) this
        else UnitValue(unit, this.value / this.unit.getBaseCoefficient() * unit.getBaseCoefficient())
    }

    override fun equals(other: Any?): Boolean {
        return if (other is UnitValue<*>) {
            if (this.unit == other.unit)
                this.value == other.value
            else if (this.unit::class == other.unit::class)
                this.toUnit(other.unit as T) == other
            else false
        } else false
    }

    override fun toString(): String {
        return "$value $unit"
    }
}