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
enum class ExerciseForceType {
    @SerialName("PUSH")
    Push,

    @SerialName("PULL")
    Pull,

    @SerialName("DYNAMIC_STRETCHING")
    DynamicStretching,

    @SerialName("DYNAMIC_STRETCHING")
    StaticStretching,

    @SerialName("COMPRESSION")
    Compression,

    @SerialName("ISOMETRIC")
    Isometric,

    @SerialName("STATIC")
    Static,

    @SerialName("HINGE")
    Hinge,

    @SerialName("OTHER")
    Other;

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