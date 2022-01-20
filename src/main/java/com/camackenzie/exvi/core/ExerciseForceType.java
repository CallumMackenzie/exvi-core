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
    Push,
    Pull,
    DynamicStretch,
    StaticStretch,
    Compression,
    Isometric,
    Static,
    Hinge;

    public static ExerciseForceType fromString(String s) {
    }
}
