/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core;

import com.google.gson.Gson;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author callum
 */
public class APIResult<T> implements Future<T> {

    private static Gson gson = new Gson();

    private final Future<HttpResponse<String>> requestFuture;
    private final Class<T> tClass;

    public APIResult(Class<T> tClass,
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
    public T get() throws InterruptedException, ExecutionException {
        return this.getFromInternalFutureGet(this.requestFuture.get());
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.getFromInternalFutureGet(this.requestFuture.get(timeout, unit));
    }

    private T getFromInternalFutureGet(HttpResponse<String> resp) {
        String responseBody = resp.body();
        return gson.fromJson(responseBody, this.tClass);
    }

    public Class<T> getResultClass() {
        return this.tClass;
    }

}
