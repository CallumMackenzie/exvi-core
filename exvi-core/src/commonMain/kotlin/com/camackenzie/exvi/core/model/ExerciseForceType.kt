/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.model.EnumUtils.formatName

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
enum class ExerciseForceType {
    PUSH, PULL, DYNAMIC_STRETCHING, STATIC_STRETCHING, COMPRESSION, ISOMETRIC, STATIC, HINGE, OTHER;

    override fun toString(): String {
        return formatName(super.toString())
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromString(s: String): ExerciseForceType? {
            return EnumUtils.enumFromString<ExerciseForceType>(s)
        }
    }
}