package com.camackenzie.exvi.core.model

@kotlinx.serialization.Serializable
data class SingleExerciseSet(
    val reps: Int,
    val weight: Mass,
    val timing: Array<Time>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SingleExerciseSet

        if (reps != other.reps) return false
        if (weight != other.weight) return false
        if (!timing.contentEquals(other.timing)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reps
        result = 31 * result + weight.hashCode()
        result = 31 * result + timing.contentHashCode()
        return result
    }
}