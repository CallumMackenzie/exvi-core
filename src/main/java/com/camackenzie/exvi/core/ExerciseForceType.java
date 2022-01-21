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
public enum ExerciseForceType {
    PUSH,
    PULL,
    DYNAMIC_STRETCH,
    STATIC_STRETCH,
    COMPRESSION,
    ISOMETRIC,
    STATIC,
    HINGE;

    public static ExerciseForceType fromString(String s) {
        return EnumUtils.enumFromString(ExerciseForceType.class, s);
    }

    @Override
    public String toString() {
        return EnumUtils.formatName(super.toString());
    }
}
