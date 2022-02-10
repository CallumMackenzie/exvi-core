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
public class ComputationFuture<T> implements Future<T> {

    private final ThreadCompletionFuture future;
    private final Computation<T> computation;

    public ComputationFuture(Computation<T> r) {
        this.future = new ThreadCompletionFuture(
                new NotifyingThread(r));
        this.computation = r;
    }

    public void startComputation() {
        this.future.getThread().start();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return this.future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return this.future.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        this.future.get();
        this.future.getThread().join();
        return this.computation.getResult();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        this.future.get(timeout, unit);
        this.future.getThread().join();
        return this.computation.getResult();
    }

    public FutureWrapper<T> wrapped() {
        return new FutureWrapper(this);
    }

    public static <T> ComputationFuture<T> startComputation(Computation r) {
        ComputationFuture<T> ret = new ComputationFuture(r);
        ret.startComputation();
        return ret;
    }

}
