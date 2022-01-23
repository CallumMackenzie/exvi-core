/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author callum
 */
public class FutureWrapper<T> implements Future<T> {

    private final Future<T> internalFuture;

    public FutureWrapper(Future<T> inf) {
        this.internalFuture = inf;
    }

    public Future<T> getInternalFuture() {
        return this.internalFuture;
    }

    @Override
    public boolean cancel(boolean arg0) {
        return this.internalFuture.cancel(arg0);
    }

    @Override
    public boolean isCancelled() {
        return this.internalFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.internalFuture.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return this.internalFuture.get();
    }

    @Override
    public T get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
        return this.internalFuture.get();
    }

    public T getFailOnError() {
        try {
            return this.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T getFailOnError(long a, TimeUnit b) {
        try {
            return this.get(a, b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
