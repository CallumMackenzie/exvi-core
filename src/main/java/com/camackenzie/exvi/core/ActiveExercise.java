/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core;

/**
 *
 * @author callum
 */
public class ActiveExercise {

    private final Exercise exercise;
    private ExerciseSet targetSets, activeSets;

    public ActiveExercise(Exercise ex, ExerciseSet targetSets, ExerciseSet activeSets) {
        this.exercise = ex;
        this.targetSets = targetSets;
        this.activeSets = activeSets;
    }

    public ActiveExercise(ExerciseSet ex) {
        this(ex.getExercise(), ex,
                new ExerciseSet(ex.getExercise(), ex.getUnit(),
                        new int[ex.getSets().length]));
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public ExerciseSet getTargetSets() {
        return this.targetSets;
    }

    public ExerciseSet getActiveSets() {
        return this.activeSets;
    }

    public void setActiveSets(ExerciseSet es) {
        this.activeSets = es;
    }

}
