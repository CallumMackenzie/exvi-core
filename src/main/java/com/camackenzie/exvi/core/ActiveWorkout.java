/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core;

import java.util.ArrayList;

/**
 *
 * @author callum
 */
public class ActiveWorkout {

    private final ArrayList<ActiveExercise> exercises;
    private final String name;
    private final long startTime;
    private long endTime;

    public ActiveWorkout(String name, ArrayList<ActiveExercise> exs) {
        this.exercises = exs;
        this.name = name;
        this.startTime = System.currentTimeMillis();
    }

    public ActiveWorkout(String name, ActiveExercise... exs) {
        this(name, new ArrayList() {
            {
                for (var ex : exs) {
                    add(ex);
                }
            }
        });
    }

    public ActiveWorkout(Workout w) {
        this(w.getName(),
                w.getExercises().stream()
                        .map(ex -> new ActiveExercise(ex))
                        .toArray(sz -> new ActiveExercise[sz]));
    }

    public ArrayList<ActiveExercise> getActiveExercises() {
        return this.exercises;
    }

    public String getName() {
        return this.name;
    }

    public long getStartTimeMillis() {
        return this.startTime;
    }

    public long getEndTimeMillis() {
        return this.endTime;
    }

    public void end() {
        this.endTime = System.currentTimeMillis();
    }

}
