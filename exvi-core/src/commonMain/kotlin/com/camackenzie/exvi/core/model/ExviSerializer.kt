/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.api.*
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.ExviLogger
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.ClassSerialDescriptorBuilder
import kotlinx.serialization.descriptors.SerialDescriptor
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

    object Builtin {
        @JvmStatic
        val string = serializer<String>()

        @JvmStatic
        val integer = serializer<Int>()

        @JvmStatic
        val long = serializer<Long>()

        @JvmStatic
        val float = serializer<Float>()

        @JvmStatic
        val double = serializer<Double>()

        @JvmStatic
        fun element(ths: ClassSerialDescriptorBuilder, name: String, ser: SerialDescriptor) =
            ths.element(name, ser)
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
                contextual(StandardExercise.serializer())

                polymorphic(Exercise::class) {
                    subclass(ActualExercise::class)
                    subclass(StandardExercise::class)
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
                    subclass(EncodedStringCache::class)

                    subclass(EncodedStringCache::class)
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
                    subclass(VerificationRequest::class)

                    subclass(ActualActiveWorkout::class)
                    subclass(ActualWorkout::class)
                    subclass(ActualExerciseSet::class)
                    subclass(ActualExercise::class)
                    subclass(StandardExercise::class)
                    subclass(ActualSingleExerciseSet::class)
                    subclass(ActualBodyStats::class)
                    subclass(ActualActiveExercise::class)
                    subclass(NoneResult::class)
                    subclass(GetFriendedUsersRequest::class)
                    subclass(FriendUserRequest::class)
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
                    subclass(VerificationRequest::class)
                    subclass(GetFriendedUsersRequest::class)
                    subclass(FriendUserRequest::class)
                    subclass(GetFriendWorkouts::class)
                }
                polymorphic(GenericDataResult::class) {
                    subclass(WorkoutListResult::class)
                    subclass(ActiveWorkoutListResult::class)
                    subclass(BooleanResult::class)
                    subclass(GetBodyStatsResponse::class)
                    subclass(AccountSaltResult::class)
                    subclass(AccountAccessKeyResult::class)
                    subclass(NoneResult::class)
                    subclass(GetFriendedUsersResponse::class)
                    subclass(RemoteWorkoutResponse::class)
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
