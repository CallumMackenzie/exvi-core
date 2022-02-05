/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.model;

import com.camackenzie.exvi.core.model.Exercise;
import com.camackenzie.exvi.core.model.ExerciseEquipment;
import com.camackenzie.exvi.core.model.ExerciseExperienceLevel;
import com.camackenzie.exvi.core.model.ExerciseForceType;
import com.camackenzie.exvi.core.model.ExerciseMechanics;
import com.camackenzie.exvi.core.model.ExerciseType;
import com.camackenzie.exvi.core.model.Muscle;
import com.camackenzie.exvi.core.model.MuscleWorkData;
import java.util.HashSet;

/**
 *
 * @author callum
 */
public class Exercise implements Comparable<Exercise> {

    private String name,
            description,
            videoLink,
            tips,
            overview;
    private MuscleWorkData[] muscleWorkData;
    private HashSet<ExerciseType> exerciseTypes;
    private ExerciseExperienceLevel experienceLevel;
    private ExerciseMechanics mechanics;
    private ExerciseForceType forceType;
    private HashSet<ExerciseEquipment> equipment;

    public Exercise(String name,
            String desc,
            String video,
            String tips,
            String overview,
            MuscleWorkData[] mwd,
            HashSet<ExerciseType> et,
            ExerciseExperienceLevel el,
            ExerciseMechanics em,
            ExerciseForceType eft,
            HashSet<ExerciseEquipment> eq) {
        this.name = name;
        this.description = desc;
        this.videoLink = video;
        this.tips = tips;
        this.overview = overview;
        this.muscleWorkData = mwd;
        this.exerciseTypes = et;
        this.experienceLevel = el;
        this.mechanics = em;
        this.forceType = eft;
        this.equipment = eq;
    }

    private Exercise() {
    }

    public boolean worksMuscle(Muscle m) {
        for (var muscleData : this.getMusclesWorked()) {
            if (muscleData.getMuscle().isInvolvedIn(m)) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public String getTips() {
        return this.tips;
    }

    public void setTips(String t) {
        this.tips = t;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String o) {
        this.overview = o;
    }

    public String getVideoLink() {
        return this.videoLink;
    }

    public void setVideoLink(String l) {
        this.videoLink = l;
    }

    public MuscleWorkData[] getMusclesWorked() {
        return this.muscleWorkData;
    }

    public void setMuslcesWorked(MuscleWorkData[] mwd) {
        this.muscleWorkData = mwd;
    }

    public HashSet<ExerciseType> getExerciseTypes() {
        return this.exerciseTypes;
    }

    public void setExerciseType(HashSet<ExerciseType> ex) {
        this.exerciseTypes = ex;
    }

    public ExerciseForceType getForceType() {
        return this.forceType;
    }

    public void setForceType(ExerciseForceType ft) {
        this.forceType = ft;
    }

    public ExerciseMechanics getMechanics() {
        return this.mechanics;
    }

    public void setMechanics(ExerciseMechanics m) {
        this.mechanics = m;
    }

    public HashSet<ExerciseEquipment> getEquipment() {
        return this.equipment;
    }

    public void setEquipment(HashSet<ExerciseEquipment> eq) {
        this.equipment = eq;
    }

    public ExerciseExperienceLevel getExperienceLevel() {
        return this.experienceLevel;
    }

    public void setExperienceLevel(ExerciseExperienceLevel st) {
        this.experienceLevel = st;
    }

    @Override
    public int compareTo(Exercise e) {
        return this.name.compareTo(e.name);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Exercise) {
            return this.compareTo((Exercise) other) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

}
