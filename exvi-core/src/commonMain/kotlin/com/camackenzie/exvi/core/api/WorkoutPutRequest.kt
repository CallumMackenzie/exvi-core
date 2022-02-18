/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.Workout
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.SelfSerializable
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
@Serializable
class WorkoutPutRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workouts: Array<Workout>
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String, workouts: Array<Workout>) : this(
        username.cached(),
        accessKey.cached(),
        workouts
    )

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        const val uid = "WorkoutPutRequest"
    }
}