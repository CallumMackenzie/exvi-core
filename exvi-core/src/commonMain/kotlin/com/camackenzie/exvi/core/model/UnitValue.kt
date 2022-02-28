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
class UnitValue<T : Unit> private constructor(
    private var iUnit: T,
    private var iValue: Double
) {
    val unit
        get() = iUnit
    val value
        get() = iValue

    private fun toOtherValue(unit: T): Double = iValue / iUnit.getBaseCoefficient() * unit.getBaseCoefficient()

    /**
     * Returns a new UnitValue with this unit converted to the given unit
     * Does not modify this object
     */
    fun toUnit(unit: T): UnitValue<T> {
        return if (unit == iUnit) this
        else UnitValue(unit, toOtherValue(unit))
    }

    /**
     * Converts this unit value to the other and returns this object
     * Modifies this object
     */
    fun asUnit(unit: T): UnitValue<T> {
        if (unit == iUnit) this
        else {
            iValue = toOtherValue(unit)
            iUnit = unit
        }
        return this
    }

    private inline fun applyToValue(other: UnitValue<T>, apply: (Double) -> Double): UnitValue<T> =
        UnitValue(iUnit, apply(other.toUnit(iUnit).iValue))

    private inline fun applyAsValue(other: UnitValue<T>, apply: (Double) -> kotlin.Unit) =
        apply(other.toOtherValue(iUnit))

    // Unit binary operators
    operator fun plus(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { iValue + it }
    operator fun minus(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { iValue - it }
    operator fun times(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { iValue * it }
    operator fun div(other: UnitValue<T>): UnitValue<T> = applyToValue(other) { iValue / it }

    // Integer binary operators
    operator fun plus(i: Int): UnitValue<T> = UnitValue(iUnit, iValue + i)
    operator fun minus(i: Int): UnitValue<T> = UnitValue(iUnit, iValue - i)
    operator fun times(i: Int): UnitValue<T> = UnitValue(iUnit, iValue * i)
    operator fun div(i: Int): UnitValue<T> = UnitValue(iUnit, iValue / i)

    // Double binary operators
    operator fun plus(i: Double): UnitValue<T> = UnitValue(iUnit, iValue + i)
    operator fun minus(i: Double): UnitValue<T> = UnitValue(iUnit, iValue - i)
    operator fun times(i: Double): UnitValue<T> = UnitValue(iUnit, iValue * i)
    operator fun div(i: Double): UnitValue<T> = UnitValue(iUnit, iValue / i)

    // Unit assignment operators
    operator fun plusAssign(other: UnitValue<T>) = applyAsValue(other) { iValue += it }
    operator fun minusAssign(other: UnitValue<T>) = applyAsValue(other) { iValue -= it }
    operator fun timesAssign(other: UnitValue<T>) = applyAsValue(other) { iValue *= it }
    operator fun divAssign(other: UnitValue<T>) = applyAsValue(other) { iValue /= it }

    // Integer assignment operators
    operator fun plusAssign(i: Int) {
        iValue += i
    }

    operator fun minusAssign(i: Int) {
        iValue -= i
    }

    operator fun timesAssign(i: Int) {
        iValue *= i
    }

    operator fun divAssign(i: Int) {
        iValue /= i
    }

    // Double assignment operators
    operator fun plusAssign(i: Double) {
        iValue += i
    }

    operator fun minusAssign(i: Double) {
        iValue -= i
    }

    operator fun timesAssign(i: Double) {
        iValue *= i
    }

    operator fun divAssign(i: Double) {
        iValue /= i
    }

    // Unary operators
    operator fun unaryMinus(): UnitValue<T> = UnitValue(iUnit, -iValue)
    operator fun unaryPlus(): UnitValue<T> = UnitValue(iUnit, +iValue)

    override fun equals(other: Any?): Boolean {
        return if (other is UnitValue<*>) {
            if (this.iUnit == other.iUnit)
                this.iValue == other.iValue
            else if (this.iUnit::class == other.iUnit::class)
                this.toUnit(other.iUnit as T) == other
            else false
        } else false
    }

    override fun toString(): String = "$iValue ${iUnit.toString().lowercase()}s"
}