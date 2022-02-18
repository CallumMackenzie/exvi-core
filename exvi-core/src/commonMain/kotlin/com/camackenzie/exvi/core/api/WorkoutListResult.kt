/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.Workout
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class WorkoutListResult(val workouts: Array<Workout>) : GenericDataResult(uid) {

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        const val uid = "WorkoutListResult"
    }
}