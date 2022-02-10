/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

import com.camackenzie.exvi.core.util.EncodedStringCache;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.AbstractMap;

/**
 *
 * @author callum
 */
public class GenericDataResult<T> extends DataResult<T> {

    private static final Gson gson = new Gson();

    private final EncodedStringCache responseClassString;

    public GenericDataResult(int code, String msg, Class<T> resClass) {
        super(code, msg, null);
        this.responseClassString = new EncodedStringCache(resClass.getCanonicalName());
    }

    public GenericDataResult(String msg, T resp) {
        super(0, msg, resp);
        this.responseClassString = new EncodedStringCache(resp.getClass().getCanonicalName());
    }

    public GenericDataResult(T resp) {
        this("Success", resp);
    }

    public String getResponseClassString() {
        return this.responseClassString.get();
    }

    public Class<T> getResponseClass() {
        try {
            return (Class<T>) Class.forName(this.getResponseClassString());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public T getResult() {
        if (super.getResult() instanceof JsonElement) {
            return gson.fromJson((JsonElement) super.getResult(), this.getResponseClass());
        } else if (this.getResponseClass().isInstance(super.getResult())) {
            return (T) super.getResult();
        } else if (super.getResult() instanceof AbstractMap) {
            return gson.fromJson(gson.toJson(super.getResult()), this.getResponseClass());
        } else {
            throw new RuntimeException("Body could not be parsed: Class is " + super.getResult().getClass());
        }
    }

}
