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

}