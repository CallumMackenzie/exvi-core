/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 *
 * @author callum
 */
public class GenericDataResult<T> extends DataResult<T> {

    private static final Gson gson = new Gson();

    private final Class<T> responseClass;

    public GenericDataResult(int code, String msg, Class<T> resClass) {
        super(code, msg, null);
        this.responseClass = resClass;
    }

    public GenericDataResult(String msg, T resp) {
        super(0, msg, resp);
        this.responseClass = (Class<T>) resp.getClass();
    }

    public GenericDataResult(T resp) {
        this("Success", resp);
    }

    public Class<T> getResponseClass() {
        return this.responseClass;
    }

    @Override
    public T getResult() {
        if (super.getResult() instanceof JsonElement) {
            return gson.fromJson((JsonElement) super.getResult(), this.responseClass);
        } else if (this.responseClass.isInstance(super.getResult())) {
            return (T) super.getResult();
        } else {
            throw new RuntimeException("Body could not be parsed: Class is " + super.getResult().getClass());
        }
    }

}
