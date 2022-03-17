package com.camackenzie.exvi.core.model

import kotlinx.datetime.Clock
import kotlin.math.floor
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

// TODO: Fix this
fun Time.mapToFormats(
    formatTo: Set<TimeUnit> = TimeUnit.values().toSet()
): Map<TimeUnit, Time> {
    fun floorDiff(value: Time): Time = value - value.floor()

    var years = this.toUnit(TimeUnit.Year)
    var weeks = floorDiff(years).weeks
    var days = floorDiff(weeks).days
    var hours = floorDiff(days).hours
    var minutes = floorDiff(hours).minutes
    var seconds = floorDiff(minutes).seconds
    var millis = floorDiff(seconds).milliseconds

    println("y: $years")

    years.floorSelf()
    weeks.floorSelf()
    days.floorSelf()
    hours.floorSelf()
    minutes.floorSelf()
    seconds.floorSelf()
    millis.floorSelf()

    val ret = HashMap<TimeUnit, Time>()
    if (formatTo.contains(TimeUnit.Year)) ret[TimeUnit.Year] = years
    if (formatTo.contains(TimeUnit.Week)) ret[TimeUnit.Week] = weeks
    if (formatTo.contains(TimeUnit.Hour)) ret[TimeUnit.Hour] = hours
    if (formatTo.contains(TimeUnit.Minute)) ret[TimeUnit.Minute] = minutes
    if (formatTo.contains(TimeUnit.Second)) ret[TimeUnit.Second] = seconds
    if (formatTo.contains(TimeUnit.Millisecond)) ret[TimeUnit.Millisecond] = millis
    return ret
}

fun Time.formatToDate(formatTo: Set<TimeUnit> = TimeUnit.values().toSet()): String {
    val ret = StringBuilder()
    val map = mapToFormats(formatTo)

    if (map.containsKey(TimeUnit.Year)) ret.append(" ").append(map[TimeUnit.Year]!!.years)
    if (map.containsKey(TimeUnit.Week)) ret.append(" ").append(map[TimeUnit.Week]!!.weeks)
    if (map.containsKey(TimeUnit.Day)) ret.append(" ").append(map[TimeUnit.Day]!!.days)
    if (map.containsKey(TimeUnit.Hour)) ret.append(" ").append(map[TimeUnit.Hour]!!.hours)
    if (map.containsKey(TimeUnit.Minute)) ret.append(" ").append(map[TimeUnit.Minute]!!.minutes)
    if (map.containsKey(TimeUnit.Second)) ret.append(" ").append(map[TimeUnit.Second]!!.seconds)
    if (map.containsKey(TimeUnit.Millisecond)) ret.append(" ").append(map[TimeUnit.Millisecond]!!.milliseconds)

    return ret.toString().trim()
}