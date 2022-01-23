/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.async;

import com.camackenzie.exvi.core.async.FutureWrapper;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 *
 * @author callum
 */
public class SharedMethodFuture<T> implements Future<T> {

    private final FutureWrapper<T> parent;
    private final Supplier<T> parentMethod;

    public SharedMethodFuture(FutureWrapper<T> parent, Supplier<T> parentMethod) {
        this.parent = parent;
        this.parentMethod = parentMethod;
    }

    @Override
    public boolean cancel(boolean arg0) {
        return this.parent.cancel(arg0);
    }

    @Override
    public boolean isCancelled() {
        return this.parent.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.parent.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        this.parent.get();
        return this.parentMethod.get();
    }

    @Override
    public T get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
        this.parent.get(arg0, arg1);
        return this.parentMethod.get();
    }

    public FutureWrapper<T> wrapped() {
        return new FutureWrapper(this);
    }

}
