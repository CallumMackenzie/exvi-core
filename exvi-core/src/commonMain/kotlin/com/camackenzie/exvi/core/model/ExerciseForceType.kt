/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EnumUtils
import com.camackenzie.exvi.core.util.EnumUtils.formatName
import kotlinx.serialization.SerialName

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
@Suppress("unused")
enum class ExerciseForceType {
    @SerialName("PUSH")
    Push,

    @SerialName("PULL")
    Pull,

    @SerialName("DYNAMIC_STRETCHING")
    DynamicStretching,

    @SerialName("STATIC_STRETCHING")
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

    override fun toString(): String = formatName(super.toString())

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromString(s: String): ExerciseForceType? = EnumUtils.enumFromString<ExerciseForceType>(s)
    }
}
