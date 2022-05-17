import com.camackenzie.exvi.core.model.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

val exerciseSet = arrayOf(
    ActualExercise(
        name = "A",
        musclesWorked = emptyArray(),
        exerciseTypes = HashSet(),
        experienceLevel = ExerciseExperienceLevel.Beginner,
        mechanics = ExerciseMechanics.Compound,
        forceType = ExerciseForceType.DynamicStretching,
        equipment = HashSet()
    ), ActualExercise(
        name = "B",
        musclesWorked = emptyArray(),
        exerciseTypes = HashSet(),
        experienceLevel = ExerciseExperienceLevel.Advanced,
        mechanics = ExerciseMechanics.Compound,
        forceType = ExerciseForceType.Compression,
        equipment = HashSet()
    )
)

fun createTestWorkout() = Workout("test", "desc", listOf(
    Exercise(
        "test", "desc", "link", "tips", "overview",
        arrayOf(Muscle.Abs.workData(1.0)),
        hashSetOf(ExerciseType.Strength),
        ExerciseExperienceLevel.Beginner,
        ExerciseMechanics.Compound,
        ExerciseForceType.Compression,
        hashSetOf(ExerciseEquipment("Equipment"))
    ),
    StandardExercise("A"),
    Exercise(
        "B", "desc", "link", "tips", "overview",
        arrayOf(Muscle.Abs.workData(1.0)),
        hashSetOf(ExerciseType.Strength),
        ExerciseExperienceLevel.Beginner,
        ExerciseMechanics.Compound,
        ExerciseForceType.Compression,
        hashSetOf(ExerciseEquipment("Equipment"))
    )
).map {
    ExerciseSet(
        it, "rep", listOf(
            SingleExerciseSet(10)
        )
    )
})

/*
 * Copyright (c) Callum Mackenzie 2022.
 */
class TestWorkout {

    @Test
    fun testTryStandardize() {
        StandardExercise.setStandardExerciseSet(exerciseSet)
        val workout = createTestWorkout()
        val result = workout.tryStandardize()
        assertTrue(result.isNotEmpty())
        assertContains(result, 2)
        assertEquals(2, result[0])
    }

}