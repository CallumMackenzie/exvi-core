/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.model.EnumUtils.formatName
import com.camackenzie.exvi.core.model.EnumUtils

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
enum class ExerciseExperienceLevel {
    BEGINNER, INTERMEDIATE, ADVANCED;

    override fun toString(): String {
        return formatName(super.toString())
    }

    companion object {
        fun fromString(s: String): ExerciseExperienceLevel? {
            return EnumUtils.enumFromString<ExerciseExperienceLevel>(s)
        }
    }
}