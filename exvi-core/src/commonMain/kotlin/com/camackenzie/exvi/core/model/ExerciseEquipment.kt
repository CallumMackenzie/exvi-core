/*
 * Copyright (c) Callum Mackenzie 2022.
 */
package com.camackenzie.exvi.core.model

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class ExerciseEquipment(val name: String) {

    override fun equals(other: Any?): Boolean = if (other is ExerciseEquipment) {
        name.trim().equals(other.name.trim(), ignoreCase = true)
    } else false

    override fun hashCode(): Int = name.hashCode()
}