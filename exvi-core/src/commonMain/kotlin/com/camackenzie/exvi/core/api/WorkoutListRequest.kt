/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.model.ExviSerializer
import com.camackenzie.exvi.core.util.EncodedStringCache
import com.camackenzie.exvi.core.util.cached
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlin.jvm.JvmStatic

/**
 *
 * @author callum
 */
@Serializable

@Suppress("unused")
data class WorkoutListRequest(
    val username: EncodedStringCache,
    val accessKey: EncodedStringCache,
    val type: Type
) : GenericDataRequest(uid) {

    constructor(username: String, accessKey: String, type: Type)
            : this(username.cached(), accessKey.cached(), type)

    @Serializable
    enum class Type {
        ListAllTemplates,
        ListAllActive
    }

    override fun toJson(): String = ExviSerializer.toJson(this)
    override fun getUID(): String = uid

    companion object {
        const val uid = "WorkoutListRequest"
    }
}