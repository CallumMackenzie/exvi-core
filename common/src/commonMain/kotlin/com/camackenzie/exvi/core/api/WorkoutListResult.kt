/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.Workout

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class WorkoutListResult(val workouts: Array<Workout>)