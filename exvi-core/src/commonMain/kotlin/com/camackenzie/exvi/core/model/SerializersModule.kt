/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.ExviLogger
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ExviSerializer {

    var serializer = Json {
        serializersModule = Json.serializersModule + SerializersModule {
            polymorphic(ActiveExercise::class) {
                subclass(ActualActiveExercise::class)
            }
            polymorphic(Workout::class) {
                subclass(ActualWorkout::class)
            }
            polymorphic(SingleExerciseSet::class) {
                subclass(ActualSingleExerciseSet::class)
            }
            polymorphic(ExerciseSet::class) {
                subclass(ActualExerciseSet::class)
            }
            polymorphic(ActiveExercise::class) {
                subclass(ActualActiveExercise::class)
            }
            polymorphic(Exercise::class) {
                subclass(ActualExercise::class)
            }
        }
        isLenient = true
    }

    inline fun <reified T> toJson(value: T): String = try {
        serializer.encodeToString(value)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") {
            "PRIMARY SERIALIZATION FAILURE: Falling back to secondary serialization technique"
        }
        Json.encodeToString(value)
    }

    inline fun <reified T> fromJson(json: String): T = try {
        serializer.decodeFromString(json)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") {
            "PRIMARY DESERIALIZATION FAILURE: Falling back to secondary deserialization technique"
        }
        Json.decodeFromString(json)
    }
}