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
enum class ExerciseForceType {
    Push,
    Pull,
    DynamicStretching,
    StaticStretching,
    Compression,
    Isometric,
    Static,
    Hinge,
    Other;

    override fun toString(): String = formatName(super.toString())

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromString(s: String): ExerciseForceType? = EnumUtils.enumFromString<ExerciseForceType>(s)
    }
}
