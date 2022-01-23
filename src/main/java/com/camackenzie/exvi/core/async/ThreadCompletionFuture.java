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
public class ThreadCompletionFuture<T extends NotifyingThread> implements Future<T> {

    private final T thread;
    private volatile boolean complete, cancelled;

    public ThreadCompletionFuture(T thread) {
        this.thread = thread;
        this.thread.addListener(new ThreadListener());
    }

    public T getThread() {
        return this.thread;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (mayInterruptIfRunning && !this.isDone()) {
            this.thread.interrupt();
            this.cancelled = true;
        }
        return false;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.complete || this.cancelled;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        while (!this.isDone()) {
            synchronized (this) {
                this.wait(50);
            }
        }
        return this.thread;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long startWait = System.currentTimeMillis(),
                maxWait = unit.toMillis(timeout);
        while (!this.isDone()) {
            if (System.currentTimeMillis() - startWait < maxWait) {
                throw new TimeoutException();
            }
            synchronized (this) {
                this.wait(50);
            }
        }
        return this.thread;
    }

    private class ThreadListener implements ThreadCompleteListener {

        @Override
        public void notifyThreadComplete(NotifyingThread nf) {
            complete = true;
        }

    }

    public FutureWrapper<T> wrapped() {
        return new FutureWrapper(this);
    }

}
