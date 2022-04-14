/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ActualActiveWorkout
import com.camackenzie.exvi.core.model.ActualWorkout
import com.camackenzie.exvi.core.model.ExviSerializer
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
data class WorkoutPutRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workouts: Array<ActualWorkout>
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String, workouts: Array<ActualWorkout>) : this(
        username.cached(),
        accessKey.cached(),
        workouts
    )

    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as WorkoutPutRequest
        if (username != other.username) return false
        if (accessKey != other.accessKey) return false
        if (!workouts.contentEquals(other.workouts)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + accessKey.hashCode()
        result = 31 * result + workouts.contentHashCode()
        return result
    }

    companion object {
        const val uid = "WorkoutPutRequest"
    }
}

@Serializable

@Suppress("unused")
data class ActiveWorkoutPutRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workouts: Array<ActualActiveWorkout>
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String, workouts: Array<ActualActiveWorkout>) : this(
        username.cached(),
        accessKey.cached(),
        workouts
    )

    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ActiveWorkoutPutRequest
        if (username != other.username) return false
        if (accessKey != other.accessKey) return false
        if (!workouts.contentEquals(other.workouts)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + accessKey.hashCode()
        result = 31 * result + workouts.contentHashCode()
        return result
    }

    companion object {
        const val uid = "ActiveWorkoutPutRequest"
    }

}