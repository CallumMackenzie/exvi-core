package com.camackenzie.exvi.core.model

typealias Time = UnitValue<TimeUnit>

@kotlinx.serialization.Serializable
enum class TimeUnit(private val unit: Double) : Unit {
    Second(1.0),
    Millisecond(Second.unit * 100.0),
    Minute(Second.unit / 60.0),
    Hour(Minute.unit / 60.0),
    Day(Hour.unit / 24.0),
    Week(Day.unit / 7.0);

    override fun getBaseCoefficient(): Double = unit
}

fun Time.none(): Time = Time(TimeUnit.Second, 0.0)