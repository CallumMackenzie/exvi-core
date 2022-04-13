/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.*
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.ExviLogger
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import kotlin.native.concurrent.ThreadLocal

// FIXME: This class can't be initialized
object ExviSerializer {

    private val defaultJsonConfig: JsonBuilder.() -> Unit = {
        isLenient = true
        coerceInputValues = true
        ignoreUnknownKeys = true
    }
    val serializer: Json

    init {
        this.serializer = try {
            Json {
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
                    polymorphic(GenericDataRequest::class) {
                        subclass(WorkoutPutRequest::class)
                        subclass(WorkoutListRequest::class)
                        subclass(ActiveWorkoutPutRequest::class)
                        subclass(DeleteWorkoutsRequest::class)
                        subclass(GetBodyStatsRequest::class)
                        subclass(SetBodyStatsRequest::class)
                        subclass(CompatibleVersionRequest::class)
                        subclass(AccountCreationRequest::class)
                        subclass(LoginRequest::class)
                        subclass(RetrieveSaltRequest::class)
                    }
                    polymorphic(GenericDataResult::class) {
                        subclass(NoneResult::class)
                        subclass(WorkoutListResult::class)
                        subclass(ActiveWorkoutListResult::class)
                        subclass(BooleanResult::class)
                        subclass(GetBodyStatsResponse::class)
                        subclass(AccountSaltResult::class)
                        subclass(AccountAccessKeyResult::class)
                    }
                    this@ExviSerializer.defaultJsonConfig(this@Json)
                }
            }
        } catch (e: Throwable) {
            ExviLogger.e(e, tag = "CORE") { "Could not initialize ExviSerializer" }
            throw e
        }
    }

    fun <T> toJson(serializer: SerializationStrategy<T>, value: T): String = try {
        ExviSerializer.serializer.encodeToString(serializer, value)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") {
            "PRIMARY SERIALIZATION FAILURE: Falling back to secondary serialization technique"
        }
        Json.encodeToString(serializer, value)
    }

    fun <T> fromJson(deserializer: DeserializationStrategy<T>, json: String): T = try {
        ExviSerializer.serializer.decodeFromString(deserializer, json)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") {
            "PRIMARY SERIALIZATION FAILURE: Falling back to secondary serialization technique"
        }
        Json.decodeFromString(deserializer, json)
    }

    inline fun <reified T> toJson(value: T): String = try {
        ExviSerializer.serializer.encodeToString(value)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") {
            "PRIMARY SERIALIZATION FAILURE: Falling back to secondary serialization technique"
        }
        Json.encodeToString(value)
    }

    inline fun <reified T> fromJson(json: String): T = try {
        ExviSerializer.serializer.decodeFromString(json)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") {
            "PRIMARY DESERIALIZATION FAILURE: Falling back to secondary deserialization technique"
        }
        Json.decodeFromString(json)
    }
}
