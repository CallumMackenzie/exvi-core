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

    fun toJson(): String {
        withJsonHeader()
        return Json.encodeToString(this)
    }

    fun failed(): Boolean {
        return statusCode != 200
    }

    fun succeeded(): Boolean {
        return !failed()
    }

    companion object {
        fun <T> jsonResult(statusCode: Int, body: T): APIResult<T> {
            return APIResult(statusCode, body, HashMap<String, String>()).withJsonHeader()
        }

        fun <T> jsonString(statusCode: Int, body: T): String {
            return jsonResult(statusCode, body).toJson()
        }
    }
}

inline fun <reified T> APIResult<String>.decodeBody(): T {
    return Json.decodeFromString(this.body)
}