/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.Serializable

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
data class WorkoutListRequest(
    override val username: EncodedStringCache,
    override val accessKey: EncodedStringCache,
    val type: Type
) : GenericDataRequest(), ValidatedUserRequest {

    constructor(username: String, accessKey: String, type: Type)
            : this(username.cached(), accessKey.cached(), type)

    @Serializable
    enum class Type {
        ListAllTemplates {
            override val workoutType = WorkoutType.Template
        },
        ListAllActive {
            override val workoutType = WorkoutType.Active
        },
        ListLatestActive {
            override val workoutType = WorkoutType.Active
        };

        abstract val workoutType: WorkoutType
    }

    @Serializable
    enum class WorkoutType {
        Active,
        Template,
    }
}