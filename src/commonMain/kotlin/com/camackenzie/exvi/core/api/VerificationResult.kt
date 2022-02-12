/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.api.DataResult

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class VerificationResult : DataResult<Nothing> {
    constructor(err: Int, msg: String) : super(err, msg, null) {}
}