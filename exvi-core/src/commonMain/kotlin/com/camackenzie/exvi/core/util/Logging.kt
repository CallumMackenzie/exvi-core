/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.util

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

val ExviLogger = Napier

private val defaultPriorityMap = mapOf(
    LogLevel.VERBOSE to "\uD83D\uDFE2 [VERBOSE]",
    LogLevel.DEBUG to "\uD83D\uDFE3 [DEBUG]",
    LogLevel.INFO to "\uD83D\uDD35 [INFO]",
    LogLevel.WARNING to "\uD83D\uDFE0 [WARN]",
    LogLevel.ERROR to "\uD83D\uDD34 [ERROR]",
    LogLevel.ASSERT to "\uD83D\uDFE1 [ASSERT]",
)

fun setupDefaultLogger(
    output: (String) -> Unit
) {
    ExviLogger.takeLogarithm()
    ExviLogger.base(object : Antilog() {
        override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {

            // Build final message
            val msg = StringBuilder()
                .append(defaultPriorityMap[priority]).append(" ")
            if (tag != null) msg.append(tag).append(": ");
            if (message != null) msg.append(message);
            if (throwable != null) msg.append("\n\t").append(throwable.message).append("\n\t")
                .append(throwable.stackTraceToString().split("\\s*\\n+\\s*").reduce { a, b ->
                    a + "\n\t\t" + b
                })

            // Log the completed log
            output(msg.toString());
        }
    })
}