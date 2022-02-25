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
enum class Muscle(subMuscles: Array<Muscle>, vararg altNames: String) : SelfSerializable {
    PECTORALIS_MAJOR("pecs"),
    TRAPEZIUS("traps"),
    RHOMBOIDS(),
    LATISSIMUS_DORSI(
        "lats",
        "latissimus"
    ),
    ERECTOR_SPINAE("erector"),
    BICEPS_BRACHII(),
    BICEPS_BRACHIALIS(), TRICEPS("tricep"), FOREARMS("forearm"),
    ANTERIOR_HEAD(),
    LATERAL_HEAD(),
    POSTERIOR_HEAD(), QUADRICEPS(
        "quads",
        "quad"
    ),
    ABDUCTORS(),
    ADDUCTORS(),
    GLUTES(),
    IT_BAND(),
    HIP_FLEXORS(),
    GASTROCNEMIUS(),
    SOLEUS(),
    RECTUS_ABDOMINIS(),
    TRANSEVERSE_ABDOMINIS(),
    INTERNAL_OBLIQUES(),
    EXTERNAL_OBLIQUES(),
    HAMSTRINGS(
        arrayOf(
            ABDUCTORS, ADDUCTORS, IT_BAND
        )
    ),
    BICEPS(arrayOf(BICEPS_BRACHIALIS, BICEPS_BRACHII)), LOWER_BACK(ERECTOR_SPINAE), UPPER_BACK(
        arrayOf(
            TRAPEZIUS,
            RHOMBOIDS
        )
    ),
    NECK(), OBLIQUES(
        arrayOf(
            INTERNAL_OBLIQUES, EXTERNAL_OBLIQUES
        )
    ),
    PALMAR_FASCIA("grip"), PLANTAL_FASCIA("feet"), BACK(arrayOf(LOWER_BACK, UPPER_BACK, LATISSIMUS_DORSI)), CHEST(
        arrayOf(
            PECTORALIS_MAJOR
        )
    ),
    ARMS(arrayOf(BICEPS, PALMAR_FASCIA, TRICEPS, FOREARMS)), SHOULDERS(
        arrayOf(
            ANTERIOR_HEAD,
            LATERAL_HEAD,
            POSTERIOR_HEAD
        )
    ),
    LEGS(
        arrayOf(
            QUADRICEPS, GLUTES, HAMSTRINGS
        )
    ),
    CALVES(arrayOf(GASTROCNEMIUS, SOLEUS)), ABS(arrayOf(RECTUS_ABDOMINIS, OBLIQUES, TRANSEVERSE_ABDOMINIS));

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

    constructor(sub: Muscle) : this(arrayOf<Muscle>(sub)) {}
    constructor(vararg altNames: String) : this(arrayOf<Muscle>(), *altNames) {}
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