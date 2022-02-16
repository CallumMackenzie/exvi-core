/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api

import com.camackenzie.exvi.core.util.SelfSerializable
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*

/**
 *
 * @author callum
 */
@kotlinx.serialization.Serializable
class APIRequest<T : SelfSerializable> {
    val body: T

    @kotlinx.serialization.Transient
    var headers: HashMap<String, String> = HashMap()

    @kotlinx.serialization.Transient
    var endpoint: String = ""

    constructor(endpoint: String, body: T, headers: HashMap<String, String> = HashMap()) {
        this.endpoint = endpoint
        this.body = body
        this.headers = headers
    }

    constructor(body: T, headers: HashMap<String, String> = HashMap()) : this("", body, headers)

    constructor(other: APIRequest<*>, newBody: T) {
        endpoint = other.endpoint
        body = newBody
        headers = other.headers
    }

    fun withJsonHeader(): APIRequest<T> {
        headers["content-type"] = "application/json"
        return this
    }

    fun sendAsync(callback: (APIResult<String>) -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            send(callback)
        }
    }

    fun hasEndpoint(): Boolean {
        return endpoint.isBlank()
    }

    fun sendAsync(callback: (HttpResponse, String) -> Unit) {
        CoroutineScope(Dispatchers.Default).launch {
            send(callback)
        }
    }

    suspend fun send(callback: (APIResult<String>) -> Unit): Job {
        return send() { response, body ->
            val parsedResponse: APIResult<String> = APIResult(response.status.value, body, HashMap())
            callback(parsedResponse)
        }
    }

    suspend fun send(callback: (HttpResponse, String) -> Unit): Job = coroutineScope {
        return@coroutineScope launch {
            HttpClient {
                expectSuccess = false
            }.use { httpClient ->
                val reqHeaders = headers
                val reqBody = body.toJson()
                val response = httpClient.post<HttpResponse>(endpoint) {
                    headers {
                        for ((key, value) in reqHeaders) {
                            append(key, value)
                        }
                    }
                    body = reqBody
                }
                callback(response, response.receive())
            }
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        suspend fun <T : SelfSerializable> request(
            endpoint: String,
            body: T,
            headers: HashMap<String, String> = HashMap(),
            callback: (APIResult<String>) -> Unit
        ): Job {
            val req = APIRequest(endpoint, body)
            for ((key, value) in headers) {
                req.headers[key] = value
            }
            return req.send() { result ->
                callback(result)
            }
        }

        @kotlin.jvm.JvmStatic
        fun <T : SelfSerializable> requestAsync(
            endpoint: String,
            body: T,
            headers: HashMap<String, String> = HashMap(),
            callback: (APIResult<String>) -> Unit
        ) {
            val req = APIRequest(endpoint, body)
            for ((key, value) in headers) {
                req.headers[key] = value
            }
            req.sendAsync() { result ->
                callback(result)
            }
        }

        @kotlin.jvm.JvmStatic
        fun jsonHeaders(): HashMap<String, String> {
            val ret: HashMap<String, String> = HashMap()
            ret["content-type"] = "application/json"
            return ret
        }
    }

}
