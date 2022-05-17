import com.camackenzie.exvi.core.api.*
import com.camackenzie.exvi.core.model.*
import kotlin.test.*

/*
 * Copyright (c) Callum Mackenzie 2022.
 */

class TestSerialization {

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
        val serialized = ExviSerializer.toJson(active as ActiveExercise)
        val des = ExviSerializer.fromJson<ActiveExercise>(serialized)
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
        val s = ExviSerializer.toJson(req as GenericDataRequest)
        val des = ExviSerializer.fromJson<GenericDataRequest>(s)
        assertTrue { des is T }
        assertEquals(des, req)
    }

    @Test
    fun testPolymorphicSerializeWorkoutPutRequest() =
        testRequestSerialization(WorkoutPutRequest("sadsd", "asdsa", emptyArray()))

    @Test
    fun testPolymorphicSerializeWorkoutListRequest() =
        testRequestSerialization(WorkoutListRequest("dsad", "213123", WorkoutListRequest.Type.ListAllActive))

    @Test
    fun testPolymorphicSerializeActiveWorkoutPutRequest() =
        testRequestSerialization(ActiveWorkoutPutRequest("dasad", "asdsa", emptyArray()))

    @Test
    fun testPolymorphicSerializeDeleteWorkoutsRequest() =
        testRequestSerialization(
            DeleteWorkoutsRequest(
                "asd",
                "asds",
                emptyArray(),
                DeleteWorkoutsRequest.WorkoutType.Workout
            )
        )

    @Test
    fun testPolymorphicSerializeGetBodyStatsRequest() =
        testRequestSerialization(GetBodyStatsRequest("ads", "asdsdasdsad"))

    @Test
    fun testPolymorphicSerializeSetBodyStatsRequest() =
        testRequestSerialization(SetBodyStatsRequest("123", "asd", ActualBodyStats.average()))

    @Test
    fun testPolymorphicSerializeCompatibleVersionRequest() =
        testRequestSerialization(CompatibleVersionRequest(121321323))

    @Test
    fun testPolymorphicSerializeAccountCreationRequest() =
        testRequestSerialization(AccountCreationRequest("213123", "asd", "adssds"))

    @Test
    fun testPolymorphicSerializeLoginRequest() =
        testRequestSerialization(LoginRequest("asdsad2323221323a", "asdsadasdsad"))

    @Test
    fun testPolymorphicSerializeRetrieveSaltRequest() = testRequestSerialization(RetrieveSaltRequest(""))

    private inline fun <reified T : GenericDataResult> testResponseSerialization(req: T) {
        val s = ExviSerializer.toJson(req as GenericDataResult)
        val des = ExviSerializer.fromJson<GenericDataResult>(s)
        assertTrue { des is T }
        assertEquals(des, req)
    }

    @Test
    fun testPolymorphicSerializeWorkoutListResult() = testResponseSerialization(
        WorkoutListResult(emptyArray())
    )

    @Test
    fun testPolymorphicSerializeActiveWorkoutListResult() = testResponseSerialization(
        ActiveWorkoutListResult(emptyArray())
    )

    @Test
    fun testPolymorphicSerializeBooleanResult() = testResponseSerialization(
        BooleanResult(true)
    )

    @Test
    fun testPolymorphicSerializeGetBodyStatsResponse() = testResponseSerialization(
        GetBodyStatsResponse(ActualBodyStats.average())
    )

    @Test
    fun testPolymorphicSerializeAccountSaltResult() = testResponseSerialization(
        AccountSaltResult("asdsad")
    )

    @Test
    fun testPolymorphicSerializeAccountAccessKeyResult() = testResponseSerialization(
        AccountAccessKeyResult("sadasd")
    )

    @Test
    fun testAPIRequestNonGeneric() {
        val request = APIRequest(
            "", WorkoutListRequest(
                "", "",
                WorkoutListRequest.Type.ListAllTemplates
            )
        )
        val serialized = ExviSerializer.toJson(request)
        val des = ExviSerializer.fromJson<APIRequest<WorkoutListRequest>>(serialized)
        assertEquals(request.endpoint, des.endpoint)
        assertEquals(request.body.username, des.body.username)
    }

    @Test
    fun testAPIRequestGeneric() {
        val request = APIRequest(
            "",
            GetBodyStatsRequest("adjks", "") as GenericDataRequest
        )
        val serialized = ExviSerializer.toJson(request)
        val des = ExviSerializer.fromJson<APIRequest<GenericDataRequest>>(serialized)
        assertEquals(des.endpoint, request.endpoint)
        assertTrue(des.body is GetBodyStatsRequest)
        des as APIRequest<GetBodyStatsRequest>
        request as APIRequest<GetBodyStatsRequest>
        assertEquals(des.body.username, request.body.username)
    }

    @Test
    fun testSerializeNoneResult() {
        val ser = ExviSerializer.toJson<GenericDataResult>(NoneResult())
        val og = ExviSerializer.fromJson<GenericDataResult>(ser)
        assertTrue { og is NoneResult }

        val ser2 = ExviSerializer.toJson(NoneResult())
        val og2 = ExviSerializer.fromJson<NoneResult>(ser2)
    }

}