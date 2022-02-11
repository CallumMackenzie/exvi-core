/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model;

/**
 *
 * @author callum
 */
public class ExerciseSet {

    private final Exercise exercise;
    private int[] sets;
    private String unit;

    public ExerciseSet(Exercise ex, String unit, int... sets) {
        this.exercise = ex;
        this.sets = sets;
    }

    public void setSet(int index, int count) {
        this.sets[index] = count;
    }

    public int getSet(int index) {
        return this.sets[index];
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public int[] getSets() {
        return this.sets;
    }

    public void setSets(int... sets) {
        this.sets = sets;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static ExerciseSet repSets(Exercise ex, int... sets) {
        return new ExerciseSet(ex, "rep", sets);
    }

    public static ExerciseSet secondSets(Exercise ex, int... sets) {
        return new ExerciseSet(ex, "second", sets);
    }

    public static ExerciseSet minuteSets(Exercise ex, int... sets) {
        return new ExerciseSet(ex, "minute", sets);
    }

}
