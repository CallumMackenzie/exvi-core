/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

/**
 *
 * @author callum
 */
public class DataResult<T> {

    private final T result;
    private final int error;
    private final String message;

    public DataResult(int err, String msg, T res) {
        this.error = err;
        this.message = msg;
        this.result = res;
    }

    public T getResult() {
        return this.result;
    }

    public int getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }

}
