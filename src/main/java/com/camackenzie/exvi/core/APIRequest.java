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
import java.io.Reader;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    public Future<APIResult<T>> send() {
        HttpClient client = HttpClient.newHttpClient();
        Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(this.endpoint));
        for (var header : this.headers.entrySet()) {
            requestBuilder.setHeader(header.getKey(),
                    header.getValue());
        }
        requestBuilder.POST(BodyPublishers.ofString(gson.toJson(this.body)));
        HttpRequest request = requestBuilder.build();

        return new APIResultFutureWrapper<T>((Class<T>) this.body.getClass(),
                client.sendAsync(request, BodyHandlers.ofString()));
    }

    public static <T> APIRequest<T> fromJson(String json) {
        return gson.fromJson(json, APIRequest.class);
    }

    public static <T> APIRequest<T> fromJson(Reader json) {
        return gson.fromJson(json, APIRequest.class);
    }

    public static <T> Future<APIResult<T>> send(String endpoint,
            T body,
            HashMap<String, String> headers) {
        return new APIRequest(endpoint, body, headers).send();
    }

    public static <T> Future<APIResult<T>> sendJson(String endpoint, T body) {
        return new APIRequest(endpoint, body).withJsonHeader().send();
    }

    private class APIResultFutureWrapper<T> implements Future<APIResult<T>> {

        private final transient Future<HttpResponse<String>> requestFuture;
        private final transient Class<T> tClass;
        private APIResult<T> result;

        public APIResultFutureWrapper(Class<T> tClass,
                CompletableFuture<HttpResponse<String>> sendAsync) {
            this.requestFuture = sendAsync;
            this.tClass = tClass;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return this.requestFuture.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return this.requestFuture.isCancelled();
        }

        @Override
        public boolean isDone() {
            return this.requestFuture.isDone();
        }

        @Override
        public APIResult<T> get() throws InterruptedException, ExecutionException {
            return this.getFromInternalFutureGet(this.requestFuture.get());
        }

        @Override
        public APIResult<T> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.getFromInternalFutureGet(this.requestFuture.get(timeout, unit));
        }

        private APIResult<T> getFromInternalFutureGet(HttpResponse<String> resp) {
            if (this.result == null) {
                String responseBody = resp.body();
                try {
                    // Format headers
                    HashMap<String, String> headers = new HashMap<>();
                    this.requestFuture.get()
                            .headers()
                            .map()
                            .entrySet()
                            .stream()
                            .forEach(en -> {
                                String headerVal = en.getValue().size() == 0 ? "" : en.getValue().get(0);
                                headers.put(en.getKey(), headerVal);
                            });
                    // Return proper api result
                    return new APIResult(this.requestFuture.get().statusCode(),
                            gson.fromJson(responseBody, this.tClass), headers);
                } catch (InterruptedException e) {
                    System.out.println(e);
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    System.err.println(e);
                }
                return null;
            } else {
                return this.result;
            }
        }

        public Class<T> getResultClass() {
            return this.tClass;
        }

    }

}
