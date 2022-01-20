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
    Kilogram(1),
    Pound(2.20462),
    Gram(1000);

    private final double unit;

    private MassUnit(double u) {
        this.unit = u;
    }

    @Override
    public double getBaseCoefficient() {
        return this.unit;
    }
}
