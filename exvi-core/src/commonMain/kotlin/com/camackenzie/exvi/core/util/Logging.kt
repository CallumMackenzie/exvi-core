/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.util

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import kotlin.jvm.JvmOverloads

val ExviLogger = Napier

private val defaultPriorityMap = mapOf(
    LogLevel.VERBOSE to "➖ - [VERBOSE]",
    LogLevel.DEBUG to "⚙ - [DEBUG]",
    LogLevel.INFO to "ℹ - [INFO]",
    LogLevel.WARNING to "⚠ - [WARN]",
    LogLevel.ERROR to "❌ - [ERROR]",
    LogLevel.ASSERT to "⛔ - [ASSERT]",
)

@JvmOverloads
@Suppress("unused")
fun setDefaultLogger(
    output: (String) -> Unit, priorityMap: Map<LogLevel, String> = defaultPriorityMap,
) {
    ExviLogger.takeLogarithm()
    ExviLogger.base(object : Antilog() {
        override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
            // Build final message
            val msg = StringBuilder()
                .append(priorityMap[priority] ?: defaultPriorityMap[priority]).append(" ")
            if (tag != null) msg.append(tag).append(": ")
            if (message != null) msg.append(message)
            if (throwable != null) msg.append("\n\t").append(throwable.message).append("\n\t")
                .append(throwable.stackTraceToString().split("\\s*\\n+\\s*").reduce { a, b ->
                    a + "\n\t\t" + b
                })

            // Log the completed log
            output(msg.toString())
        }
    })
}