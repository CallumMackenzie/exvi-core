/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.model.EnumUtils.formatName
import kotlinx.serialization.SerialName

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
@Suppress("unused")
enum class ExerciseExperienceLevel {
    @SerialName("BEGINNER")
    Beginner,

    @SerialName("INTERMEDIATE")
    Intermediate,

    @SerialName("ADVANCED")
    Advanced;

    override fun toString(): String {
        return formatName(super.toString())
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromString(s: String): ExerciseExperienceLevel? {
            return EnumUtils.enumFromString<ExerciseExperienceLevel>(s)
        }
    }
}