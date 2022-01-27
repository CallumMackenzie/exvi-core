/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

/**
 *
 * @author callum
 */
public class WorkoutListRequest {

    private static enum Type {
        LIST_ALL
    }

    private final Type type;

    public WorkoutListRequest(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

}
