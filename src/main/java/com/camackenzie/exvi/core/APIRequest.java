/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscriber;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 *
 * @author callum
 */
public class APIRequest<T> {

    private static final Gson gson = new Gson();

    private T body;
    private HashMap<String, String> headers;
    private String endpoint;

    public APIRequest(String endpoint, T body, HashMap<String, String> headers) {
        this.endpoint = endpoint;
        this.body = body;
        this.headers = headers;
    }

    public APIRequest(String endpoint, T body) {
        this(endpoint, body, new HashMap<>());
    }

    public APIRequest<T> withJsonHeader() {
        this.headers.put("content-type", "application/json");
        return this;
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String e) {
        this.endpoint = e;
    }

    public APIResult send() {
        HttpClient client = HttpClient.newHttpClient();
        Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(this.endpoint));
        for (var header : this.headers.entrySet()) {
            requestBuilder.setHeader(header.getKey(),
                    header.getValue());
        }
        requestBuilder.POST(BodyPublishers.ofString(gson.toJson(this.body)));
        HttpRequest request = requestBuilder.build();

        return new APIResult(this.body.getClass(),
                client.sendAsync(request, BodyHandlers.ofString()));
    }

    public static <T> APIResult send(String endpoint,
            T body,
            HashMap<String, String> headers) {
        return new APIRequest(endpoint, body, headers).send();
    }

    public static <T> APIResult sendJson(String endpoint, T body) {
        return new APIRequest(endpoint, body).withJsonHeader().send();
    }

}
