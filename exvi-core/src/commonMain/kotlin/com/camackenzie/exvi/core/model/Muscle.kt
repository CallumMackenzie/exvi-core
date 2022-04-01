/*
 * Copyright (c) Callum Mackenzie 2022.
 */

package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.EnumUtils
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
enum class Muscle(
    subMuscles: Array<Muscle>,
    vararg altNames: String
) : SelfSerializable {
    PectoralisMajor("pecs"),
    Trapezius("traps"),
    Rhomboids(),
    LatissimusDorsi("lats", "latissimus"),
    ErectorSpinae("erector"),
    BicepsBrachii("brachii"),
    BicepsBrachialis("brachialis"),
    Triceps("tricep"),
    Forearms("forearm"),
    AnteriorHead(),
    LateralHead(),
    PosteriorHead(),
    Quadriceps("quads", "quad"),
    Abductors(),
    Adductors(),
    Glutes(),
    ITBand(),
    HipFlexors(),
    Gastrocnemius(),
    Soleus(),
    RecusAbdominis(),
    TranseverseAbdominis(),
    InternalObliques(),
    ExternalObliques(),
    Hamstrings(Abductors, Adductors, ITBand),
    Biceps(BicepsBrachialis, BicepsBrachii),
    LowerBack(ErectorSpinae),
    UpperBack(Trapezius, Rhomboids),
    Neck(),
    Obliques(InternalObliques, ExternalObliques),
    PalmarFascia("grip"),
    PlantarFascia("feet"),
    Back(LowerBack, UpperBack, LatissimusDorsi),
    Chest(PectoralisMajor),
    Arms(Biceps, PalmarFascia, Triceps, Forearms),
    Shoulders(AnteriorHead, LateralHead, PosteriorHead),
    Legs(Quadriceps, Glutes, Hamstrings),
    Calves(Gastrocnemius, Soleus),
    Abs(RecusAbdominis, Obliques, TranseverseAbdominis);

    private val altNames: Array<String>
    val subMuscles = HashSet<Muscle>()
    val superMuscles = HashSet<Muscle>()

    init {
        this.altNames = arrayOf(*altNames)
        for (muscle in subMuscles) {
            this.subMuscles.add(muscle)
        }
        for (muscle in subMuscles) {
            muscle.superMuscles.add(this)
        }
    }

    constructor(vararg altNames: String) : this(emptyArray(), *altNames) {}
    constructor(vararg muscles: Muscle) : this(arrayOf(*muscles))
    constructor() : this(arrayOf<Muscle>()) {}

    val muscleName: String
        get() = EnumUtils.formatName(super.toString())

    fun matchesName(name: String?): Boolean {
        for (n in altNames) {
            if (n.equals(name, ignoreCase = true)) {
                return true
            }
        }
        return this.muscleName.equals(name, ignoreCase = true)
    }

    fun isComposedOf(m: Muscle): Boolean = validateMuscleSuper(this, m)

    fun isComponentOf(m: Muscle): Boolean = validateMuscleSub(this, m)

    fun isInvolvedIn(m: Muscle): Boolean = isComponentOf(m) || isComposedOf(m)

    fun workData(workCoefficient: Double): MuscleWorkData =
        MuscleWorkData(this, workCoefficient)

    override fun toString(): String = muscleName

    override fun getUID(): String = uid

    override fun toJson(): String = Json.encodeToString(this)

    companion object {
        const val uid = "Muscle"

        private fun validateMuscleSub(constant: Muscle, dyn: Muscle): Boolean {
            if (constant.matchesName(dyn.muscleName)) {
                return true
            }
            for (subMuscle in dyn.subMuscles) {
                if (validateMuscleSub(constant, subMuscle)) {
                    return true
                }
            }
            return false
        }

        private fun validateMuscleSuper(constant: Muscle, dyn: Muscle): Boolean {
            if (constant.matchesName(dyn.muscleName)) {
                return true
            }
            for (superMuscle in dyn.superMuscles) {
                if (validateMuscleSuper(constant, superMuscle)) {
                    return true
                }
            }
            return false
        }
    }
}