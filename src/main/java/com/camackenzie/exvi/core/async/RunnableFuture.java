package com.camackenzie.exvi.core.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author callum
 */
public class RunnableFuture implements Future<Void> {

    private final ThreadCompletionFuture future;

    public RunnableFuture(Runnable r) {
        this.future = new ThreadCompletionFuture(
                new NotifyingThread(r));
    }

    public void start() {
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
    public Void get() throws InterruptedException, ExecutionException {
        this.future.get();
        this.future.getThread().join();
        return null;
    }

    @Override
    public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        this.future.get(timeout, unit);
        this.future.getThread().join();
        return null;
    }

    public FutureWrapper<Void> wrapped() {
        return new FutureWrapper(this);
    }

}
