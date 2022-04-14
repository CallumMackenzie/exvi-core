/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.*
import com.camackenzie.exvi.core.util.CachedKey
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.EncryptionResult
import com.camackenzie.exvi.core.util.ExviLogger
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import kotlin.jvm.JvmStatic
import kotlin.native.concurrent.ThreadLocal

object ExviSerializer {

    private val defaultJsonConfig: JsonBuilder.() -> Unit = {
        isLenient = true
        coerceInputValues = true
        ignoreUnknownKeys = true
        classDiscriminator = "exvic"
    }

    @JvmStatic
    val serializer: Json = try {
        Json {
            serializersModule = SerializersModule {
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
//                    subclass(NoneResult::class)
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

    @JvmStatic
    fun <T> toJson(serializer: SerializationStrategy<T>, value: T): String = try {
        ExviSerializer.serializer.encodeToString(serializer, value)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") { "Primary serialization failure" }
        Json.encodeToString(serializer, value)
    }

    @JvmStatic
    fun <T> fromJson(deserializer: DeserializationStrategy<T>, json: String): T = try {
        ExviSerializer.serializer.decodeFromString(deserializer, json)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") { "Primary serialization failure" }
        Json.decodeFromString(deserializer, json)
    }

    inline fun <reified T> toJson(value: T): String = try {
        ExviSerializer.serializer.encodeToString(value)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") { "Primary serialization failure" }
        Json.encodeToString(value)
    }

    inline fun <reified T> fromJson(json: String): T = try {
        ExviSerializer.serializer.decodeFromString(json)
    } catch (ex: SerializationException) {
        ExviLogger.e(ex, tag = "CORE") { "Primary serialization failure" }
        Json.decodeFromString(json)
    }

}
