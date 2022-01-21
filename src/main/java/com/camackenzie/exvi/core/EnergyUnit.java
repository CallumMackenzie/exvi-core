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
public enum EnergyUnit implements Unit {
    KILOJOULE(1),
    KILOCALORIE(0.239006);

    private final double unit;

    private EnergyUnit(double u) {
        this.unit = u;
    }

    @Override
    public double getBaseCoefficient() {
        return this.unit;
    }

}
