/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.Identifiable
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused", "UNCHECKED_CAST")
data class ActualActiveWorkout(
    override val name: String,
    override val baseWorkoutId: EncodedStringCache,
    override var exercises: Array<ActiveExercise>,
    override val activeWorkoutId: EncodedStringCache = Identifiable.generateId(),
    override var startTimeMillis: Long? = null,
    override var endTimeMillis: Long? = null
) : ActiveWorkout {
    override val serializer: KSerializer<SelfSerializable>
        get() = ActualActiveWorkout.serializer() as KSerializer<SelfSerializable>

    /**
     * Auto generated
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ActiveWorkout

        if (name != other.name) return false
        if (baseWorkoutId != other.baseWorkoutId) return false
        if (exercises != other.exercises) return false
        if (activeWorkoutId != other.activeWorkoutId) return false
        if (startTimeMillis != other.startTimeMillis) return false
        if (endTimeMillis != other.endTimeMillis) return false

        return true
    }

    /**
     * Auto generated
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + baseWorkoutId.hashCode()
        result = 31 * result + exercises.hashCode()
        result = 31 * result + activeWorkoutId.hashCode()
        result = 31 * result + (startTimeMillis?.hashCode() ?: 0)
        result = 31 * result + (endTimeMillis?.hashCode() ?: 0)
        return result
    }
}