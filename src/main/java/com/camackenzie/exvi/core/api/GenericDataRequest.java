/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

import com.camackenzie.exvi.core.util.CryptographyUtils;
import com.camackenzie.exvi.core.util.EncodedStringCache;
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
    private final EncodedStringCache username,
            accessKey;

    public GenericDataRequest(String username,
            String accessKey,
            T body) {
        this.requestClass = (Class<T>) body.getClass();
        this.body = body;
        this.username = new EncodedStringCache(username);
        this.accessKey = new EncodedStringCache(accessKey);
    }

    public Class<T> getRequestClass() {
        return this.requestClass;
    }

    public String getUsername() {
        return this.username.get();
    }

    public String getAccessKey() {
        return this.accessKey.get();
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
