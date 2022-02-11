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
public enum ExerciseType {
    STRENGTH,
    WARMUP,
    COOLDOWN,
    PLYOMETRIC,
    CONDITIONING,
    POWER_LIFTING;

    public static ExerciseType fromString(String s) {
        return EnumUtils.enumFromString(ExerciseType.class, s);
    }

    @Override
    public String toString() {
        return EnumUtils.formatName(super.toString());
    }
}
