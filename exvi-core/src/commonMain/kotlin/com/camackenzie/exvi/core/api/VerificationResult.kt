/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.None
import kotlinx.serialization.json.*
import kotlinx.serialization.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class VerificationResult : DataResult<None> {
    override val result: None? = null
    override fun toJson(): String {
        return Json.encodeToString(this)
    }

    override fun getUID(): String {
        return "VerificationResult"
    }

    constructor(err: Int, msg: String) : super(err, msg) {}
}
