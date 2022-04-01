package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import kotlinx.serialization.json.*
import kotlinx.serialization.*

@Serializable
@Suppress("unused")
class DeleteWorkoutsRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workoutIds: Array<EncodedStringCache>,
    val workoutType: WorkoutType
) : GenericDataRequest(uid) {

    @Serializable
    enum class WorkoutType {
        Workout,
        ActiveWorkout
    }

    override fun toJson(): String = Json.encodeToString(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "DeleteWorkoutsRequest"
    }
}