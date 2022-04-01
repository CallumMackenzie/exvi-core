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
enum class ExerciseExperienceLevel {
    Beginner,
    Intermediate,
    Advanced;

    override fun toString(): String = formatName(super.toString())

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromString(s: String): ExerciseExperienceLevel? = EnumUtils.enumFromString<ExerciseExperienceLevel>(s)
    }
}