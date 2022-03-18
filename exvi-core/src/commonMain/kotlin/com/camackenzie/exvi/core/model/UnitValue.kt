/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import kotlin.math.abs
import kotlin.math.floor

/**
 *
 * @author callum
 */

interface Unit {
    fun getBaseCoefficient(): Double
}

@kotlinx.serialization.Serializable
@Suppress("unused")
data class UnitValue<T : Unit>(
    private var iUnit: T,
    private var iValue: Double
) {
    val unit
        get() = iUnit
    val value
        get() = iValue

    constructor(iUnit: T, iValue: Int) : this(iUnit, iValue.toDouble())

    constructor(iUnit: T, iValue: Float): this(iUnit, iValue.toDouble())

    private fun toOtherValue(unit: T): Double = if (unit == this.unit) iValue else
        iValue / iUnit.getBaseCoefficient() * unit.getBaseCoefficient()

    @Deprecated("Unnecessary after switch to data class")
    fun valueCopy(): UnitValue<T> = UnitValue(iUnit, iValue)

    fun floor(): UnitValue<T> = UnitValue(unit, floor(value))

    fun floorSelf(): UnitValue<T> {
        iValue = floor(iValue)
        return this
    }

    fun ceil(): UnitValue<T> = UnitValue(unit, floor(value))

    fun ceilSelf(): UnitValue<T> {
        iValue = floor(iValue)
        return this
    }

    fun pairWithUnit(): Pair<T, UnitValue<T>> = Pair(unit, this)

    /**
     * Returns a new UnitValue with this unit converted to the given unit
     * Does not modify this object
     */
    fun toUnit(unit: T): UnitValue<T> = UnitValue(unit, toOtherValue(unit))

    /**
     * Converts this unit value to the other and returns this object
     * Modifies this object
     */
    @Suppress("UNUSED_EXPRESSION")
    fun asUnit(unit: T): UnitValue<T> = if (unit == iUnit) this
    else {
        iValue = toOtherValue(unit)
        iUnit = unit
        this
    }

    fun inRangeOf(other: UnitValue<T>, range: UnitValue<T>): Boolean =
        abs(toUnit(other.unit).iValue - other.iValue) <= range.iValue

    private inline fun applyToValue(other: UnitValue<T>, apply: (Double) -> Double): UnitValue<T> =
        UnitValue(iUnit, apply(other.toUnit(iUnit).iValue))

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

    // Unary operators
    operator fun unaryMinus(): UnitValue<T> = UnitValue(iUnit, -iValue)
    operator fun unaryPlus(): UnitValue<T> = UnitValue(iUnit, +iValue)

    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean = if (other is UnitValue<*>) {
        if (this.iUnit == other.iUnit)
            this.iValue == other.iValue
        else if (this.iUnit::class == other.iUnit::class)
            this.toUnit(other.iUnit as T) == other
        else false
    } else false

    override fun toString(): String = "$iValue ${iUnit.toString().lowercase()}${if (iValue != 1.0) "s" else ""}"
    override fun hashCode(): Int {
        var result = iUnit.hashCode()
        result = 31 * result + iValue.hashCode()
        return result
    }
}