/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
data class ExerciseEquipment(val name: String) {

    override fun equals(other: Any?): Boolean = if (other is ExerciseEquipment) {
        name.equals(other.name, ignoreCase = true)
    } else false
    
    override fun hashCode(): Int = name.hashCode()
}