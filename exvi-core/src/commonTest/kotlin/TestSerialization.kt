import com.camackenzie.exvi.core.api.*
import com.camackenzie.exvi.core.model.*
import com.camackenzie.exvi.core.util.ExviLogger
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import kotlin.test.*

/*
 * Copyright (c) Callum Mackenzie 2022.
 */

class TestSerialization {

    @BeforeTest
    fun initialize() {
        ExviLogger.base(object : Antilog() {
            override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
                println("[$priority]${if (tag != null) " - $tag" else ""}: ${message ?: ""} ${
                    if (throwable != null) "\n\t" else ""
                }${
                    throwable?.stackTraceToString()?.lines()?.reduce { a, b -> "$a\n\t\t$b" } ?: ""
                }")
            }
        })
    }

    @AfterTest
    fun finish() {
        ExviLogger.takeLogarithm()
    }

    private val exercises = listOf(
        Exercise(
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
    )

    @Test
    fun testSerializeActiveExercise() {
        val active = ActiveExercise(
            ExerciseSet(
                exercises[0],
                "rep",
                listOf(SingleExerciseSet(1))
            )
        )
        val serialized = ExviSerializer.toJson(active)
        val des = ExviSerializer.fromJson<ActualActiveExercise>(serialized)
        assertEquals(active.target.exercise.name, des.target.exercise.name)
        assertEquals(active.exercise.experienceLevel, des.exercise.experienceLevel)
        assertEquals(des.currentSet, active.currentSet)
    }

    @Test
    fun testSerializeExerciseSet() {
        val exerciseSet = ExerciseSet(
            exercises[0],
            "rep",
            listOf(SingleExerciseSet(1))
        )
        val serialized = exerciseSet.toJson()
        val des = ExviSerializer.fromJson<ActualExerciseSet>(serialized)
        assertEquals(exerciseSet.unit, des.unit)
        assertEquals(exerciseSet.exercise.name, des.exercise.name)
    }

    @Test
    fun testSerializeSingleExerciseSet() {
        val single = SingleExerciseSet(10, 10.pounds)
        val serialized = single.toJson()
        val des = ExviSerializer.fromJson<ActualSingleExerciseSet>(serialized)
        assertEquals(single.reps, des.reps)
        assertTrue(single.weight.inRangeOf(des.weight, 0.1.pounds))
    }

    @Test
    fun testSerializeWorkout() {
        val workout = Workout("test", "desc", exercises.map {
            ExerciseSet(
                it, "rep", listOf(
                    SingleExerciseSet(10)
                )
            )
        })
        val serialized = workout.toJson()
        val des = ExviSerializer.fromJson<ActualWorkout>(serialized)
        assertEquals(workout.name, des.name)
        assertEquals(workout.description, des.description)
        assertEquals(workout.exercises[0].exercise.name, des.exercises[0].exercise.name)
        assertEquals(workout.exercises[0].sets[0].reps, des.exercises[0].sets[0].reps)
    }

    @Test
    fun testSerializeExercise() {
        val exercise = exercises[0]
        val serialized = exercise.toJson()
        val deserialized = ExviSerializer.fromJson<ActualExercise>(serialized)
        assertEquals(exercise.name, deserialized.name)
        assertEquals(exercise.experienceLevel, deserialized.experienceLevel)
        assertEquals(deserialized.equipment.size, 1)
        assertEquals(deserialized.experienceLevel, exercise.experienceLevel)
    }

    @Test
    fun testSerializeWorkoutListRequest() {
        val req1 = WorkoutListRequest("Username", "key", WorkoutListRequest.Type.ListAllActive)
        val req1Serialized = req1.toJson()
        val req1Des = ExviSerializer.fromJson<WorkoutListRequest>(req1Serialized)
        assertEquals(req1Des.username, req1.username)
        assertEquals(req1Des.accessKey, req1.accessKey)
        assertEquals(req1Des.type, req1.type)
    }

    @Test
    fun testSerializeWorkoutPutRequest() {
        val req1 = WorkoutPutRequest(
            "username", "key", arrayOf(
                Workout("Test")
            )
        )
        val req1Serialized = req1.toJson()
        val req1Des = ExviSerializer.fromJson<WorkoutPutRequest>(req1Serialized)
        assertEquals(req1Des.username, req1.username)
        assertEquals(req1Des.accessKey, req1.accessKey)
        assertEquals(req1Des.workouts[0].name, req1.workouts[0].name)
    }

    private inline fun <reified T : GenericDataRequest> testRequestSerialization(req: T) {
        val s = ExviSerializer.toJson(req)
        val des = ExviSerializer.fromJson<GenericDataRequest>(s)
        assertTrue { des is T }
    }

    @Test
    fun testPolymorphicSerializeLoginRequest() = testRequestSerialization(LoginRequest("", ""))

    @Test
    fun testPolymorphicSerializeWorkoutPutRequest() = testRequestSerialization(WorkoutPutRequest("", "", emptyArray()))

    @Test
    fun testPolymorphicSerializeWorkoutListRequest() =
        testRequestSerialization(WorkoutListRequest("", "", WorkoutListRequest.Type.ListAllActive))

    @Test
    fun testPolymorphicSerializeActiveWorkoutPutRequest() =
        testRequestSerialization(ActiveWorkoutPutRequest("", "", emptyArray()))

    @Test
    fun testPolymorphicSerializeDeleteWorkoutsRequest() =
        testRequestSerialization(DeleteWorkoutsRequest("", "", emptyArray(), DeleteWorkoutsRequest.WorkoutType.Workout))

    @Test
    fun testPolymorphicSerializeGetBodyStatsRequest() = testRequestSerialization(GetBodyStatsRequest("", ""))

    // TODO: Add more polymorphic serialization request tests & result tests

}