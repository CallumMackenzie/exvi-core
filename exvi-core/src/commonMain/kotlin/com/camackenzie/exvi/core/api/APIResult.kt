/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 *
 * @author callum
 */
@Serializable
@Suppress("unused")
class APIResult<T> {
    var body: T
    var statusCode: Int
    var headers: HashMap<String, String>

    constructor(statusCode: Int, body: T, headers: HashMap<String, String>) {
        this.statusCode = statusCode
        this.body = body
        this.headers = headers
    }

    constructor(other: APIResult<*>, newBody: T) {
        body = newBody
        statusCode = other.statusCode
        headers = other.headers
    }

    fun withJsonHeader(): APIResult<T> {
        headers["content-type"] = "application/json"
        return this
    }

    fun failed(): Boolean = statusCode != 200

    fun succeeded(): Boolean = !failed()

    companion object {
        @kotlin.jvm.JvmStatic
        fun <T> jsonResult(statusCode: Int, body: T): APIResult<T> =
            APIResult(statusCode, body, HashMap()).withJsonHeader()
    }
}

@Suppress("unused")
inline fun <reified T> APIResult<String>.decodeBody(): T = Json.decodeFromString<T>(this.body)

fun APIResult<String>.toJson(): String = Json.encodeToString(this)