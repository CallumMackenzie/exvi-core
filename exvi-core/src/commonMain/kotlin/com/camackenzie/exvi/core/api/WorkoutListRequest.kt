/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class WorkoutListRequest(val type: Type) : SelfSerializable {
    enum class Type {
        LIST_ALL
    }

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return Companion.getUID()
    }

    companion object {
        @kotlin.jvm.JvmStatic
        @kotlin.jvm.JvmName("UID")
        fun getUID(): String {
            return "WorkoutListRequest"
        }
    }
}