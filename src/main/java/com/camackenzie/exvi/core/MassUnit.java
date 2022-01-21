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
public enum MassUnit implements Unit {
    KILOGRAM(1),
    POUND(2.20462),
    GRAM(1000);

    private final double unit;

    private MassUnit(double u) {
        this.unit = u;
    }

    @Override
    public double getBaseCoefficient() {
        return this.unit;
    }
}
