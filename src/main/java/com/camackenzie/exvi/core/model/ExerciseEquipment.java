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
public class ExerciseEquipment {

    private final String name;

    public ExerciseEquipment(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ExerciseEquipment) {
            return this.name.equalsIgnoreCase(((ExerciseEquipment) other).name);
        }
        return false;
    }
}
