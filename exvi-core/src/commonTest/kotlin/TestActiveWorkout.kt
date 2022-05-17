import com.camackenzie.exvi.core.model.StandardExercise
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/*
 * Copyright (c) Callum Mackenzie 2022.
 */

class TestActiveWorkout {

    fun createTestActiveWorkout() = createTestWorkout().newActiveWorkout()

    @Test
    fun testTryStandardize() {
        StandardExercise.setStandardExerciseSet(exerciseSet)
        val workout = createTestActiveWorkout()
        val result = workout.tryStandardize()

        println(workout.exercises[2].exercise)

        assertTrue(result.isNotEmpty())
        assertEquals(2, result[0])
    }

}