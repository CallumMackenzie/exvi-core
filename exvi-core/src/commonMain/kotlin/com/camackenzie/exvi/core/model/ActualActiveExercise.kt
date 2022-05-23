/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Suppress("unused", "UNCHECKED_CAST")
data class ActualActiveExercise(
    override val target: ExerciseSet,
    override var active: ExerciseSet,
    override var currentSet: Int = 0
) : ActiveExercise {
    override val serializer: KSerializer<SelfSerializable>
        get() = ActualActiveExercise.serializer() as KSerializer<SelfSerializable>
}