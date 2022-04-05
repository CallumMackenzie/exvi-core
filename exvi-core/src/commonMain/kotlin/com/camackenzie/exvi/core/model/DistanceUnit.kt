/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

typealias Distance = UnitValue<DistanceUnit>

val Number.centimeters get() = Distance(DistanceUnit.Centimeter, toDouble())
val Number.meters get() = Distance(DistanceUnit.Meter, toDouble())
val Number.kilometers get() = Distance(DistanceUnit.Kilometer, toDouble())
val Number.inches get() = Distance(DistanceUnit.Inch, toDouble())
val Number.feet get() = Distance(DistanceUnit.Foot, toDouble())

val Distance.centimeters get() = toUnit(DistanceUnit.Centimeter)
val Distance.meters get() = toUnit(DistanceUnit.Meter)
val Distance.kilometers get() = toUnit(DistanceUnit.Kilometer)
val Distance.inches get() = toUnit(DistanceUnit.Inch)
val Distance.feet get() = toUnit(DistanceUnit.Foot)

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
@Suppress("unused")
enum class DistanceUnit(private val unit: Double) : ValueUnit {
    Centimeter(1.0),
    Meter(Centimeter.unit / 100.0),
    Kilometer(Meter.unit / 1000.0),
    Inch(0.39370078740109),
    Foot(Inch.unit / 12.0);

    override fun getBaseCoefficient(): Double = unit

    companion object {
        fun none(): Distance = Distance(Centimeter, 0.0)
    }
}
