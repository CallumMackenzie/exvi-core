/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActualActiveWorkout
import com.camackenzie.exvi.core.model.ActualWorkout
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
class WorkoutPutRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workouts: Array<ActualWorkout>
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String, workouts: Array<ActualWorkout>) : this(
        username.cached(),
        accessKey.cached(),
        workouts
    )

    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "WorkoutPutRequest"
    }
}

@Serializable
@Suppress("unused")
class ActiveWorkoutPutRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workouts: Array<ActualWorkout>
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String, workouts: Array<ActualWorkout>) : this(
        username.cached(),
        accessKey.cached(),
        workouts
    )

    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "ActiveWorkoutPutRequest"
    }

}