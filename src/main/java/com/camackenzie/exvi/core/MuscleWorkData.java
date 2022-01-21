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
public class MuscleWorkData {

    private final Muscle muscle;
    private final double workCoeff;

    public MuscleWorkData(Muscle m, double workCoeff) {
        this.muscle = m;
        this.workCoeff = workCoeff;
    }

    public Muscle getMuscle() {
        return this.muscle;
    }

    public double getWorkCoeff() {
        return this.workCoeff;
    }

}
