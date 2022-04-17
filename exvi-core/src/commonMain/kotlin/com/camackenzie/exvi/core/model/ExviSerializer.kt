/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.*
import com.camackenzie.exvi.core.util.ExviLogger
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.jvm.JvmStatic

@Suppress("unused")
object ExviSerializer {

    private val defaultJsonConfig: JsonBuilder.() -> Unit = {
        isLenient = true
        coerceInputValues = true
        ignoreUnknownKeys = true
        classDiscriminator = "exclazz"
    }

    @JvmStatic
    val serializer: Json = try {
        Json {
            serializersModule = SerializersModule {
                contextual(ActualExercise.serializer())
                contextual(ActualSingleExerciseSet.serializer())
                contextual(ActualExerciseSet.serializer())
                contextual(ActualActiveExercise.serializer())
                contextual(ActualWorkout.serializer())
                contextual(ActualActiveWorkout.serializer())

                polymorphic(Exercise::class) {
                    subclass(ActualExercise::class)
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
                polymorphic(Workout::class) {
                    subclass(ActualWorkout::class)
                }
                polymorphic(ActiveWorkout::class) {
                    subclass(ActualActiveWorkout::class)
                }
                polymorphic(SelfSerializable::class) {
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
                    subclass(WorkoutListResult::class)
                    subclass(ActiveWorkoutListResult::class)
                    subclass(BooleanResult::class)
                    subclass(GetBodyStatsResponse::class)
                    subclass(AccountSaltResult::class)
                    subclass(AccountAccessKeyResult::class)

                    subclass(ActualActiveWorkout::class)
                    subclass(ActualWorkout::class)
                    subclass(ActualExerciseSet::class)
                    subclass(ActualExercise::class)
                    subclass(ActualSingleExerciseSet::class)
                    subclass(ActualBodyStats::class)
                    subclass(ActualActiveExercise::class)
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
        Json { this.defaultJsonConfig() }
    }

    fun <T> trySerialize(attempt: () -> T, onFail: () -> T) = try {
        attempt()
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") { "Primary serialization failure" }
        onFail()
    }

    @JvmStatic
    fun <T> toJsonElement(serializer: SerializationStrategy<T>, value: T) = trySerialize({
        this.serializer.encodeToJsonElement(serializer, value)
    }, {
        Json.encodeToJsonElement(serializer, value)
    })

    @JvmStatic
    fun <T> fromJsonElement(serializer: DeserializationStrategy<T>, value: JsonElement) = trySerialize({
        this.serializer.decodeFromJsonElement(serializer, value)
    }, {
        Json.decodeFromJsonElement(serializer, value)
    })

    @JvmStatic
    fun <T> toJson(serializer: SerializationStrategy<T>, value: T) = trySerialize({
        this.serializer.encodeToString(serializer, value)
    }, {
        Json.encodeToString(serializer, value)
    })

    @JvmStatic
    fun <T> fromJson(deserializer: DeserializationStrategy<T>, json: String): T = trySerialize({
        this.serializer.decodeFromString(deserializer, json)
    }, {
        Json.decodeFromString(deserializer, json)
    })

    inline fun <reified T> toJson(value: T) = trySerialize({
        this.serializer.encodeToString(value)
    }, {
        Json.encodeToString(value)
    })

    inline fun <reified T> fromJson(json: String): T = trySerialize({
        this.serializer.decodeFromString(json)
    }, {
        Json.decodeFromString(json)
    })

}
