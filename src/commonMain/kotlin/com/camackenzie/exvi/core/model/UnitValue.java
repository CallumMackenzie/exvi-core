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
public class UnitValue<T extends Unit> {

    private final double value;
    private final T unit;

    public UnitValue(T unit, double val) {
        this.unit = unit;
        this.value = val;
    }

    public T getUnit() {
        return this.unit;
    }

    public double getValue() {
        return this.value;
    }

    public UnitValue<T> toUnit(T m) {
        return new UnitValue(m, this.value / this.unit.getBaseCoefficient() * m.getBaseCoefficient());
    }

    @Override
    public String toString() {
        return this.getValue() + this.getUnit().toString();
    }

}
