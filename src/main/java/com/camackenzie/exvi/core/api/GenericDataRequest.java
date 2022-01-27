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
public class GenericDataRequest<T> {

    private static Gson gson = new Gson();

    private final Class<T> requestClass;
    private final T body;

    public GenericDataRequest(T body) {
        this.requestClass = (Class<T>) body.getClass();
        this.body = body;
    }

    public Class<T> getRequestClass() {
        return this.requestClass;
    }

    public T getBody() {
        if (this.body instanceof JsonElement) {
            return gson.fromJson((JsonElement) this.body, this.requestClass);
        } else if (this.requestClass.isInstance(this.body)) {
            return (T) this.body;
        } else {
            throw new RuntimeException("Body could not be parsed: Class is " + this.body.getClass());
        }
    }

}
