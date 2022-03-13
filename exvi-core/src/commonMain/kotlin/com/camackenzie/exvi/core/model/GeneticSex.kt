/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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