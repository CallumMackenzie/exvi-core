/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author callum
 */
public class ImmediateFuture<T> implements Future<T> {

    private final T data;

    public ImmediateFuture(T data) {
        this.data = data;
    }

    @Override
    public boolean cancel(boolean arg0) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return this.data;
    }

    @Override
    public T get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
        return this.data;
    }

}
