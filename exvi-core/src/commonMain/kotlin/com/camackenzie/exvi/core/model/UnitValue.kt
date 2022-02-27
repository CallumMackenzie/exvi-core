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

    private inline fun applyToValue(other: UnitValue<T>, apply: (Double) -> Double): UnitValue<T> =
        UnitValue(unit, apply(other.toUnit(unit).value))

    operator fun plus(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { value + it }
    operator fun minus(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { value - it }
    operator fun times(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { value * it }
    operator fun div(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { value / it }
    operator fun unaryMinus(): UnitValue<T> = UnitValue(unit, -value)
    operator fun unaryPlus(): UnitValue<T> = UnitValue(unit, +value)

    override fun equals(other: Any?): Boolean {
        return if (other is UnitValue<*>) {
            if (this.unit == other.unit)
                this.value == other.value
            else if (this.unit::class == other.unit::class)
                this.toUnit(other.unit as T) == other
            else false
        } else false
    }

    override fun toString(): String = "$value ${unit.toString().lowercase()}s"
}