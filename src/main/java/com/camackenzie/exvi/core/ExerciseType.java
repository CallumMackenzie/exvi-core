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
public enum ExerciseType {
    Strength,
    Warmup,
    Cooldown,
    Plyometric,
    Conditioning,
    Powerlifting;

    public static ExerciseType fromString(String s) {
    }
}
