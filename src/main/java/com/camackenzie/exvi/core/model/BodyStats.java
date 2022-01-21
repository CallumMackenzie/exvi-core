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
public class BodyStats {

    private UnitValue<MassUnit> weight;
    private UnitValue<DistanceUnit> height;

    public BodyStats(UnitValue<MassUnit> weight, UnitValue<DistanceUnit> height) {
        this.weight = weight;
        this.height = height;
    }

    public UnitValue<MassUnit> getTotalMass() {
        return this.weight;
    }

    public UnitValue<DistanceUnit> getHeight() {
        return this.height;
    }

    public void setTotalMass(UnitValue<MassUnit> mu) {
        this.weight = mu;
    }

    public void setHeight(UnitValue<DistanceUnit> mu) {
        this.height = mu;
    }

    public static BodyStats averageMale() {
        return new BodyStats(new UnitValue(MassUnit.POUND, 190),
                new UnitValue(DistanceUnit.METER, 1.7));
    }

    public static BodyStats averageFemale() {
        return new BodyStats(new UnitValue(MassUnit.POUND, 170),
                new UnitValue(DistanceUnit.METER, 1.625));
    }

    public static BodyStats average() {
        return new BodyStats(new UnitValue(MassUnit.POUND, 180),
                new UnitValue(DistanceUnit.METER, 1.68));
    }

}
