/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
enum class Muscle(
    subMuscles: Array<Muscle>,
    vararg altNames: String
) : SelfSerializable {
    @SerialName("PECTORALIS_MAJOR")
    PectoralisMajor("pecs"),

    @SerialName("TRAPEZIUS")
    Trapezius("traps"),

    @SerialName("RHOMBOIDS")
    Rhomboids(),

    @SerialName("LATISSIMUS_DORSI")
    LatissimusDorsi("lats", "latissimus"),

    @SerialName("ERECTOR_SPINAE")
    ErectorSpinae("erector"),

    @SerialName("BICEPS_BRACHII")
    BicepsBrachii(),

    @SerialName("BICEPS_BRACHIALIS")
    BicepsBrachialis(),

    @SerialName("TRICEPS")
    Triceps("tricep"),

    @SerialName("FOREARMS")
    Forearms("forearm"),

    @SerialName("ANTERIOR_HEAD")
    AnteriorHead(),

    @SerialName("LATERAL_HEAD")
    LateralHead(),

    @SerialName("POSTERIOR_HEAD")
    PosteriorHead(),

    @SerialName("QUADRICEPS")
    Quadriceps("quads", "quad"),

    @SerialName("ABDUCTORS")
    Abductors(),

    @SerialName("ADDUCTORS")
    Adductors(),

    @SerialName("GLUTES")
    Glutes(),

    @SerialName("IT_BAND")
    ITBand(),

    @SerialName("HIP_FLEXORS")
    HipFlexors(),

    @SerialName("GASTROCNEMIUS")
    Gastrocnemius(),

    @SerialName("SOLEUS")
    Soleus(),

    @SerialName("RECTUS_ABDOMINIS")
    RecusAbdominis(),

    @SerialName("TRANSEVERSE_ABDOMINIS")
    TranseverseAbdominis(),

    @SerialName("INTERNAL_OBLIQUES")
    InternalObliques(),

    @SerialName("EXTERNAL_OBLIQUES")
    ExternalObliques(),

    @SerialName("HAMSTRINGS")
    Hamstrings(Abductors, Adductors, ITBand),

    @SerialName("BICEPS")
    Biceps(BicepsBrachialis, BicepsBrachii),

    @SerialName("LOWER_BACK")
    LowerBack(ErectorSpinae),

    @SerialName("UPPER_BACK")
    UpperBack(Trapezius, Rhomboids),

    @SerialName("NECK")
    Neck(),

    @SerialName("OBLIQUES")
    Obliques(InternalObliques, ExternalObliques),

    @SerialName("PALMAR_FASCIA")
    PalmarFascia("grip"),

    @SerialName("PLANTAR_FASCIA")
    PlantarFascia("feet"),

    @SerialName("BACK")
    Back(LowerBack, UpperBack, LatissimusDorsi),

    @SerialName("CHEST")
    Chest(PectoralisMajor),

    @SerialName("ARMS")
    Arms(Biceps, PalmarFascia, Triceps, Forearms),

    @SerialName("SHOULDERS")
    Shoulders(AnteriorHead, LateralHead, PosteriorHead),

    @SerialName("LEGS")
    Legs(Quadriceps, Glutes, Hamstrings),

    @SerialName("CALVES")
    Calves(Gastrocnemius, Soleus),

    @SerialName("ABS")
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

    fun isComposedOf(m: Muscle): Boolean {
        return validateMuscleSuper(this, m)
    }

    fun isComponentOf(m: Muscle): Boolean {
        return validateMuscleSub(this, m)
    }

    fun isInvolvedIn(m: Muscle): Boolean {
        return isComponentOf(m) || isComposedOf(m)
    }

    fun workData(workCoefficient: Double): MuscleWorkData {
        return MuscleWorkData(this, workCoefficient)
    }

    override fun toString(): String {
        return muscleName
    }

    override fun getUID(): String {
        return uid
    }

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

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