import com.camackenzie.exvi.core.api.WorkoutListRequest
import com.camackenzie.exvi.core.api.WorkoutPutRequest
import com.camackenzie.exvi.core.model.*
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.test.Test
import kotlin.test.assertEquals

/*
 * Copyright (c) Callum Mackenzie 2022.
 */

class TestSerialization {

    @Test
    fun testSerializeExercise() {
        val exercise = Exercise(
            "test",
            "desc",
            "link",
            "tips",
            "overview",
            arrayOf(Muscle.Abs.workData(1.0)),
            hashSetOf(ExerciseType.Strength),
            ExerciseExperienceLevel.Beginner,
            ExerciseMechanics.Compound,
            ExerciseForceType.Compression,
            hashSetOf(ExerciseEquipment("Equipment"))
        )
        val serialized = exercise.toJson()
        val deserialized = Json.decodeFromString<ActualExercise>(serialized)
        assertEquals(exercise.name, deserialized.name)
        assertEquals(exercise.experienceLevel, deserialized.experienceLevel)
        assertEquals(deserialized.equipment.size, 1)
        assertEquals(deserialized.experienceLevel, exercise.experienceLevel)
    }

    @Test
    fun testSerializeWorkoutListRequest() {
        val req1 = WorkoutListRequest("Username", "key", WorkoutListRequest.Type.ListAllActive)
        val req1Serialized = req1.toJson()
        val req1Des = Json.decodeFromString<WorkoutListRequest>(req1Serialized)
        assertEquals(req1Des.username, req1.username)
        assertEquals(req1Des.accessKey, req1.accessKey)
        assertEquals(req1Des.type, req1.type)
    }

    @Test
    fun testSerializeWorkoutPutRequest() {
        val req1 = WorkoutPutRequest("username", "key", arrayOf(
            Workout("Test")
        ))
        val req1Serialized = req1.toJson()
        val req1Des = Json.decodeFromString<WorkoutPutRequest>(req1Serialized)
        assertEquals(req1Des.username, req1.username)
        assertEquals(req1Des.accessKey, req1.accessKey)
        assertEquals(req1Des.workouts[0].name, req1.workouts[0].name)
    }

}