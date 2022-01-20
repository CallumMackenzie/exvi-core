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
public enum DistanceUnit implements Unit {
    Centimeter(1),
    Meter(0.01),
    Inch(0.39370078740109),
    Foot(0.0328084);

    private final double unit;

    private DistanceUnit(double u) {
        this.unit = u;
    }

    @Override
    public double getBaseCoefficient() {
        return this.unit;
    }
}
