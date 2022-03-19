package com.camackenzie.exvi.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
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
    Millisecond(Second.unit * 1000.0),
    Minute(Second.unit / 60.0),
    Hour(Minute.unit / 60.0),
    Day(Hour.unit / 24.0),
    Week(Day.unit / 7.0),
    Year(Day.unit / 365.24219);

    override fun getBaseCoefficient(): Double = unit

    companion object {
        fun now(): Time = Time(Millisecond, Clock.System.now().toEpochMilliseconds().toDouble())
        fun none(): Time = Time(Second, 0.0)
        fun sortedValues(): Array<TimeUnit> = values().sortedWith { a, b -> a.unit.compareTo(b.unit) }.toTypedArray()
        fun currentYear(): Int = 1970 + now().asUnit(Year).floorSelf().value.toInt()
        fun fromDuration(duration: Duration): Time = Time(
            Millisecond,
            duration.inWholeMilliseconds.toDouble()
        )
    }
}

inline fun Time.toDuration(): Duration = when (unit) {
    TimeUnit.Millisecond -> value.milliseconds
    TimeUnit.Second -> value.seconds
    TimeUnit.Minute -> value.minutes
    TimeUnit.Hour -> value.hours
    TimeUnit.Day -> value.days
    TimeUnit.Week -> value.days * 7
    TimeUnit.Year -> value.days * 365.24219
}

fun Time.mapToFormats(
    formatTo: Set<TimeUnit> = TimeUnit.values().toSet()
): Map<TimeUnit, Time> {
    fun floorDiff(value: Time): Time = value - value.floor()
    val ret = HashMap<TimeUnit, Time>()
    var last = this.years
    ret[TimeUnit.Year] = last
    for (unit in TimeUnit.sortedValues()) {
        val converted = floorDiff(last).toUnit(unit)
        if (unit != TimeUnit.Year) ret[unit] = converted
        last = converted
    }
    ret.forEach { it.value.floorSelf() }
    return ret.filter { formatTo.contains(it.key) }
}

fun Time.timesToString(
    formatTo: Set<TimeUnit>,
    conversion: (Time, StringBuilder) -> kotlin.Unit
): String {
    val ret = StringBuilder()
    val map = mapToFormats(formatTo)
    for (timeUnit in TimeUnit.sortedValues()) {
        if (map.containsKey(timeUnit)) conversion(map[timeUnit]!!.toUnit(timeUnit), ret)
    }
    return ret.toString().trim()
}

@Suppress("unused")
fun Time.formatToElapsedTime(formatTo: Set<TimeUnit> = TimeUnit.values().toSet()): String =
    timesToString(formatTo) { time, str ->
        if (time.value.toInt() != 0)
            str.append(" ").append("${time.value.toInt()} ${time.unit.toString().lowercase()}s")
    }

@Suppress("unused")
fun Time.formatToDate(formatTo: Set<TimeUnit> = TimeUnit.values().toSet()): String =
    timesToString(formatTo) { time, str ->
        val tVal = time.value.toInt()
        when (time.unit) {
            TimeUnit.Year -> str.append("${1970 + tVal} ")
            else -> if (tVal != 0) str.append("$time ")
        }
    }

@Suppress("unused")
fun Time.toInstant(): Instant =
    Instant.fromEpochMilliseconds(this.toUnit(TimeUnit.Millisecond).value.toLong())