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

    @kotlin.jvm.Transient
    @kotlinx.serialization.Transient
    var headers: HashMap<String, String> = HashMap()

    @kotlin.jvm.Transient
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

    fun sendAsync(
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        callback: (APIResult<String>) -> Unit
    ): Job = coroutineScope.launch(dispatcher) {
        send(callback)
    }

    fun hasEndpoint(): Boolean {
        return endpoint.isBlank()
    }

    suspend fun send(callback: (APIResult<String>) -> Unit) {
        return send { response, body ->
            val parsedResponse: APIResult<String> = APIResult(response?.status?.value ?: 418, body, HashMap())
            callback(parsedResponse)
        }
    }

    suspend fun send(callback: (HttpResponse?, String) -> Unit) {
        try {
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
        } catch (e: Exception) {
            callback(null, "Could not send request")
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        suspend fun <T : SelfSerializable> request(
            endpoint: String,
            body: T,
            headers: HashMap<String, String> = jsonHeaders(),
            callback: (APIResult<String>) -> Unit
        ) = APIRequest(endpoint, body, headers).send(callback)

        @kotlin.jvm.JvmStatic
        fun <T : SelfSerializable> requestAsync(
            endpoint: String,
            body: T,
            headers: HashMap<String, String> = jsonHeaders(),
            coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
            coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
            callback: (APIResult<String>) -> Unit
        ): Job = APIRequest(endpoint, body, headers)
            .sendAsync(coroutineScope, coroutineDispatcher, callback)

        @kotlin.jvm.JvmStatic
        fun jsonHeaders(): HashMap<String, String> {
            val ret: HashMap<String, String> = HashMap()
            ret["content-type"] = "application/json"
            return ret
        }
    }

}
