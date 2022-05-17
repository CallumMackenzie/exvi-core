import com.camackenzie.exvi.core.model.*
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlin.test.*

class TestStandardExercise {

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

    @Test
    fun testTryStandardize() {
        assertNotNull(exerciseSet[0].tryStandardize())
    }

    @Test
    fun testOriginalEquality() {
        StandardExercise.setStandardExerciseSet(exerciseSet)

        val a = StandardExercise("A")
        val b = StandardExercise("B")
        assertEquals(a.forceType, exerciseSet[0].forceType)
        assertNotEquals(a.forceType, b.forceType)
    }

    @Test
    fun testSerialization() {
        StandardExercise.setStandardExerciseSet(exerciseSet)

        val a = StandardExercise("A")
        val aJson = ExviSerializer.toJson<SelfSerializable>(a)
        println(aJson)
        println(StandardExercise.standardExerciseSet)
        val aDes = ExviSerializer.fromJson<SelfSerializable>(aJson)
        assertTrue { aDes is Exercise }
        aDes as Exercise
        assertEquals(a.experienceLevel, aDes.experienceLevel)

        val aJson2 = ExviSerializer.toJson<Exercise>(a)
        println(aJson2)
        val aDes2 = ExviSerializer.fromJson<Exercise>(aJson2)
        assertEquals(a.forceType, aDes2.forceType)
    }
}