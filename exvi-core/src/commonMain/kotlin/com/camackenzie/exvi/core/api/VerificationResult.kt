/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.None
import com.camackenzie.exvi.core.util.SelfSerializable
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class VerificationResult : GenericDataResult(uid) {
    val result: None? = null

    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return Companion.uid
    }

    companion object {
        @kotlin.jvm.JvmStatic
        val uid = "VerificationResult"
    }
}
