/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model;

import com.camackenzie.exvi.core.model.ActiveWorkout;
import com.camackenzie.exvi.core.model.ExerciseSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author callum
 */
public class Workout {

    private String name;
    private String description;
    private List<ExerciseSet> exercises;

    public Workout(String name, String desc,
            List<ExerciseSet> wex) {
        this.name = name;
        this.exercises = wex;
        this.description = desc;
    }

    public Workout(String name,
            List<ExerciseSet> wex) {
        this(name, "", wex);
    }

    public Workout(String name) {
        this(name, new ArrayList<>());
    }

    public Workout(String name, String desc) {
        this(name, desc, new ArrayList<>());
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<ExerciseSet> getExercises() {
        return this.exercises;
    }

    public void setExercises(ArrayList<ExerciseSet> ex) {
        this.exercises = ex;
    }

    public void setName(String s) {
        this.name = s;
    }

    public ActiveWorkout getActiveWorkout() {
        return new ActiveWorkout(this);
    }

    public String formatToTable() {
        // Retrieve the longest exercise name
        int longestExerciseName = 0;
        for (var exercise : this.exercises) {
            String exerciseName = exercise.getExercise().getName();
            if (exerciseName.length() > longestExerciseName) {
                longestExerciseName = exerciseName.length();
            }
        }

        // Retrieve the longest string rep repersentation
        int longestRepAmount = 0;
        for (var exercise : this.exercises) {
            for (var set : exercise.getSets()) {
                String setString = set + " " + exercise.getUnit();
                if (setString.length() > longestRepAmount) {
                    longestRepAmount = setString.length();
                }
            }
        }

        // Build formatted table
        StringBuilder ret = new StringBuilder();

        for (var exercise : this.exercises) {
            // Get the number of spaces needed to fill largest space
            String exerciseName = exercise.getExercise().getName();
            int numNameFillerSpaces = longestExerciseName - exerciseName.length();
            // Append the beginning of the formatted row
            ret.append("| ")
                    .append(exerciseName)
                    .append(" ".repeat(numNameFillerSpaces))
                    .append(" | ");
            // Fill the rest of the table according to exercise set count/reps
            for (var set : exercise.getSets()) {
                // Get the number of spaces needed to fill largest space
                String setString = set + " " + exercise.getUnit();
                int numSetFillerSpaces = longestRepAmount - setString.length();
                // Append the formatted set row
                ret.append("| ")
                        .append(setString)
                        .append(" ".repeat(numSetFillerSpaces))
                        .append(" ");
            }
            ret.append("\n");
        }

        // Return formatted string
        return ret.toString();
    }

}
