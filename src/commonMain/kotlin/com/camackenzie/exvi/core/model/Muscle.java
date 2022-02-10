/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model;

import com.camackenzie.exvi.core.model.EnumUtils;
import java.util.HashSet;

/**
 *
 * @author callum
 */
public enum Muscle {
    PECTORALIS_MAJOR("pecs"),
    TRAPEZIUS("traps"),
    RHOMBOIDS(),
    LATISSIMUS_DORSI("lats", "latissimus"),
    ERECTOR_SPINAE("erector"),
    BICEPS_BRACHII(),
    BICEPS_BRACHIALIS(),
    TRICEPS("tricep"),
    FOREARMS("forearm"),
    ANTERIOR_HEAD(),
    LATERAL_HEAD(),
    POSTERIOR_HEAD(),
    QUADRICEPS("quads", "quad"),
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
    HAMSTRINGS(new Muscle[]{ABDUCTORS, ADDUCTORS, IT_BAND}),
    BICEPS(new Muscle[]{BICEPS_BRACHIALIS, BICEPS_BRACHII}),
    LOWER_BACK(ERECTOR_SPINAE),
    UPPER_BACK(new Muscle[]{TRAPEZIUS, RHOMBOIDS}),
    NECK(),
    OBLIQUES(new Muscle[]{INTERNAL_OBLIQUES, EXTERNAL_OBLIQUES}),
    PALMAR_FASCIA("grip"),
    PLANTAL_FASCIA("feet"),
    BACK(new Muscle[]{LOWER_BACK, UPPER_BACK, LATISSIMUS_DORSI}),
    CHEST(new Muscle[]{PECTORALIS_MAJOR}),
    ARMS(new Muscle[]{BICEPS, PALMAR_FASCIA, TRICEPS, FOREARMS}),
    SHOULDERS(new Muscle[]{ANTERIOR_HEAD, LATERAL_HEAD, POSTERIOR_HEAD}),
    LEGS(new Muscle[]{QUADRICEPS, GLUTES, HAMSTRINGS}),
    CALVES(new Muscle[]{GASTROCNEMIUS, SOLEUS}),
    ABS(new Muscle[]{RECTUS_ABDOMINIS, OBLIQUES, TRANSEVERSE_ABDOMINIS});

    private final String[] altNames;
    private final HashSet<Muscle> subMuscles = new HashSet<>();
    private final HashSet<Muscle> superMuscles = new HashSet<>();

    private Muscle(Muscle[] subMuscles, String... altNames) {
        this.altNames = altNames;
        for (var muscle : subMuscles) {
            this.subMuscles.add(muscle);
        }
        for (var muscle : subMuscles) {
            muscle.superMuscles.add(this);
        }
    }

    private Muscle(Muscle sub) {
        this(new Muscle[]{sub});
    }

    private Muscle(String... altNames) {
        this(new Muscle[]{}, altNames);
    }

    private Muscle() {
        this(new Muscle[]{});
    }

    public String getName() {
        return EnumUtils.formatName(super.toString());
    }

    public HashSet<Muscle> getSubMuscles() {
        return this.subMuscles;
    }

    public HashSet<Muscle> getSuperMuscles() {
        return this.superMuscles;
    }

    public boolean matchesName(String name) {
        for (var n : this.altNames) {
            if (n.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return this.getName().equalsIgnoreCase(name);
    }

    public boolean isComposedOf(Muscle m) {
        return Muscle.validateMuscleSuper(this, m);
    }

    public boolean isComponentOf(Muscle m) {
        return Muscle.validateMuscleSub(this, m);
    }

    public boolean isInvolvedIn(Muscle m) {
        return this.isComponentOf(m) || this.isComposedOf(m);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    private static boolean validateMuscleSub(Muscle constant, Muscle dyn) {
        if (constant.matchesName(dyn.getName())) {
            return true;
        }
        for (var subMuscle : dyn.getSubMuscles()) {
            if (Muscle.validateMuscleSub(constant, subMuscle)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateMuscleSuper(Muscle constant, Muscle dyn) {
        if (constant.matchesName(dyn.getName())) {
            return true;
        }
        for (var superMuscle : dyn.getSuperMuscles()) {
            if (Muscle.validateMuscleSuper(constant, superMuscle)) {
                return true;
            }
        }
        return false;
    }

}
