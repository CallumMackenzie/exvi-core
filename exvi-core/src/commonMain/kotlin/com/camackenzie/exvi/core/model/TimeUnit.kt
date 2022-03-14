package com.camackenzie.exvi.core.model

import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

typealias Time = UnitValue<TimeUnit>

val Number.seconds
    get() = Time(TimeUnit.Second, toDouble())
val Number.milliseconds
    get() = Time(TimeUnit.Millisecond, toDouble())
val Number.minutes
    get() = Time(TimeUnit.Minute, toDouble())
val Number.hours
    get() = Time(TimeUnit.Hour, toDouble())
val Number.days
    get() = Time(TimeUnit.Day, toDouble())
val Number.weeks
    get() = Time(TimeUnit.Week, toDouble())
val Number.years
    get() = Time(TimeUnit.Year, toDouble())

val Time.seconds get() = toUnit(TimeUnit.Second)
val Time.milliseconds get() = toUnit(TimeUnit.Millisecond)
val Time.minutes get() = toUnit(TimeUnit.Minute)
val Time.hours get() = toUnit(TimeUnit.Hour)
val Time.days get() = toUnit(TimeUnit.Day)
val Time.weeks get() = toUnit(TimeUnit.Week)
val Time.years get() = toUnit(TimeUnit.Year)

@kotlinx.serialization.Serializable
@Suppress("unused")
enum class TimeUnit(private val unit: Double) : Unit {
    Second(1.0),
    Millisecond(Second.unit * 100.0),
    Minute(Second.unit / 60.0),
    Hour(Minute.unit / 60.0),
    Day(Hour.unit / 24.0),
    Week(Day.unit / 7.0),
    Year(Day.unit / 364.5);

    override fun getBaseCoefficient(): Double = unit

    companion object {
        fun now(): Time = Time(Millisecond, Clock.System.now().toEpochMilliseconds().toDouble())

        fun none(): Time = Time(Second, 0.0)
    }
}


inline fun Time.toDuration(): Duration = when (unit) {
    TimeUnit.Millisecond -> value.milliseconds
    TimeUnit.Second -> value.seconds
    TimeUnit.Minute -> value.minutes
    TimeUnit.Hour -> value.hours
    TimeUnit.Day -> value.days
    TimeUnit.Week -> value.days * 7
    TimeUnit.Year -> value.days * 364.5
}