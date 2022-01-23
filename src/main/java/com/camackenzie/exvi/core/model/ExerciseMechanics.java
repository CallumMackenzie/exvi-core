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
public enum ExerciseMechanics {
    ISOLATION,
    COMPOUND,
    OTHER;

    public static ExerciseMechanics fromString(String s) {
        return EnumUtils.enumFromString(ExerciseMechanics.class, s);
    }

    @Override
    public String toString() {
        return EnumUtils.formatName(super.toString());
    }
}
