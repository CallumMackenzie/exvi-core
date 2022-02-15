/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.SelfSerializable

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
abstract class DataResult<T: SelfSerializable>(val error: Int, val message: String): SelfSerializable {
    abstract val result: T?

    fun errorOccured(): Boolean {
        return error != 0
    }
}