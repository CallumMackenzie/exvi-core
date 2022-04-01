/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EnumUtils

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
@Suppress("unused")
enum class GeneticSex {
    Male, Female, Unspecified;

    override fun toString(): String = EnumUtils.formatName(super.toString())

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromString(s: String): GeneticSex? = EnumUtils.enumFromString<GeneticSex>(s)
    }
}