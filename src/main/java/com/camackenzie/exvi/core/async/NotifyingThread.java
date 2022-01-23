/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.async;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @author callum
 */
public abstract class NotifyingThread extends Thread {

    private final Set<ThreadCompleteListener> listeners
            = new CopyOnWriteArraySet<ThreadCompleteListener>();

    public final void addListener(ThreadCompleteListener listener) {
        this.listeners.add(listener);
    }

    public final void removeListener(ThreadCompleteListener listener) {
        this.listeners.remove(listener);
    }

    private final void notifyListeners() {
        for (ThreadCompleteListener listener : this.listeners) {
            listener.notifyThreadComplete(this);
        }
    }

    @Override
    public final void run() {
        doRun();
        notifyListeners();
    }

    public abstract void doRun();
}
