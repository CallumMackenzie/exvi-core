/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EnumUtils
import com.camackenzie.exvi.core.util.EnumUtils.formatName

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
@Suppress("unused")
enum class ExerciseType {
    Strength,
    Warmup,
    Cooldown,
    Plyometric,
    Conditioning,
    PowerLifting;

    override fun toString(): String = formatName(super.toString())

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromString(s: String): ExerciseType? = EnumUtils.enumFromString<ExerciseType>(s)
    }
}