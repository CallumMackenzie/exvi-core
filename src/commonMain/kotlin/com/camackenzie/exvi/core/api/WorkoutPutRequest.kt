/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.Workout
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
@Serializable
class WorkoutPutRequest(val workouts: Array<Workout>) : SelfSerializable {
    override fun toJson(): String {
        return Json.encodeToString(this)
    }
}