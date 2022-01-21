/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model;

import com.camackenzie.exvi.core.model.ActiveWorkout;
import java.util.ArrayList;

/**
 *
 * @author callum
 */
public class WorkoutManager {

    private final ArrayList<Workout> workouts;
    private final ArrayList<ActiveWorkout> activeWorkouts;

    public WorkoutManager(ArrayList<Workout> wks,
            ArrayList<ActiveWorkout> aws) {
        this.workouts = wks;
        this.activeWorkouts = aws;
    }

    public WorkoutManager() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public ArrayList<Workout> getWorkouts() {
        return this.workouts;
    }

    public Workout getNamedWorkout(String name) {
        for (var w : this.workouts) {
            if (w.getName().equalsIgnoreCase(name)) {
                return w;
            }
        }
        return null;
    }

    public void addWorkout(Workout w) {
        this.workouts.add(w);
    }

    public ArrayList<ActiveWorkout> getActiveWorkouts() {
        return this.activeWorkouts;
    }

    public void addActiveWorkout(ActiveWorkout w) {
        this.activeWorkouts.add(w);
    }

}
