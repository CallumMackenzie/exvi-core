package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ExviSerializer
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.json.*
import kotlinx.serialization.*

@Serializable

@Suppress("unused")
data class DeleteWorkoutsRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val workoutIds: Array<EncodedStringCache>,
    val workoutType: WorkoutType
) : GenericDataRequest(uid) {

    constructor(
        username: String, accessKey: String, workoutIds: Array<EncodedStringCache>,
        workoutType: WorkoutType
    ) : this(username.cached(), accessKey.cached(), workoutIds, workoutType)

    @Serializable
    enum class WorkoutType {
        Workout,
        ActiveWorkout
    }

    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as DeleteWorkoutsRequest
        if (username != other.username) return false
        if (accessKey != other.accessKey) return false
        if (!workoutIds.contentEquals(other.workoutIds)) return false
        if (workoutType != other.workoutType) return false
        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + accessKey.hashCode()
        result = 31 * result + workoutIds.contentHashCode()
        result = 31 * result + workoutType.hashCode()
        return result
    }

    companion object {
        const val uid = "DeleteWorkoutsRequest"
    }
}