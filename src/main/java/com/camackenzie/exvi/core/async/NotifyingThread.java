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
public class NotifyingThread extends Thread {

    private final Set<ThreadCompleteListener> listeners
            = new CopyOnWriteArraySet<ThreadCompleteListener>();

    public NotifyingThread(Runnable r) {
        super(r);
    }

    public void addAllListeners(ThreadCompleteListener... listeners) {
        for (var listener : listeners) {
            this.addListener(listener);
        }
    }

    public void removeAllListeners(ThreadCompleteListener... listeners) {
        for (var listener : listeners) {
            this.removeListener(listener);
        }
    }

    public void addListener(ThreadCompleteListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ThreadCompleteListener listener) {
        this.listeners.remove(listener);
    }

    private void notifyListeners() {
        for (var listener : this.listeners) {
            listener.notifyThreadComplete(this);
        }
    }

    @Override
    public final void run() {
        super.run();
        this.notifyListeners();
    }
}
