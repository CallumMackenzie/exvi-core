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
public enum ExerciseExperienceLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public static ExerciseExperienceLevel fromString(String s) {
        return EnumUtils.enumFromString(ExerciseExperienceLevel.class, s);
    }

    @Override
    public String toString() {
        return EnumUtils.formatName(super.toString());
    }
}
