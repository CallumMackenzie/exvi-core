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
data class ActualWorkout(
    override var name: String = "",
    override var description: String = "",
    override val exercises: ArrayList<ExerciseSet> = arrayListOf(),
    override val id: EncodedStringCache = Identifiable.generateId(),
    override var public: Boolean = false
) : Workout {

    constructor(other: Workout) : this(
        name = other.name,
        description = other.description,
        exercises = ArrayList(other.exercises.map { it.toActual() }),
        id = other.id.copy(),
        public = other.public
    )

    override fun newActiveWorkout(): ActiveWorkout = ActiveWorkout(this)

    override val serializer: KSerializer<SelfSerializable>
        get() = ActualWorkout.serializer() as KSerializer<SelfSerializable>
}