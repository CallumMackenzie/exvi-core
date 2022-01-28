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
import com.google.gson.internal.LinkedTreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author callum
 */
public class GenericDataRequest<T> {

    private static Gson gson = new Gson();

    private final EncodedStringCache requestClassString;
    private final T body;
    private final EncodedStringCache username,
            accessKey;

    public GenericDataRequest(String username,
            String accessKey,
            T body) {
        this.requestClassString = new EncodedStringCache(body.getClass().getCanonicalName());
        this.body = body;
        this.username = new EncodedStringCache(username);
        this.accessKey = new EncodedStringCache(accessKey);
    }

    public String getRequestClassString() {
        return this.requestClassString.get();
    }

    public String getUsername() {
        return this.username.get();
    }

    public String getAccessKey() {
        return this.accessKey.get();
    }

    public Class<T> getRequestClass() {
        try {
            return (Class<T>) Class.forName(this.getRequestClassString());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public T getBody() {
        if (this.body instanceof JsonElement) {
            return gson.fromJson((JsonElement) this.body, this.getRequestClass());
        } else if (this.body instanceof LinkedTreeMap) {
            return gson.fromJson(gson.toJson((LinkedTreeMap) this.body), this.getRequestClass());
        } else if (this.getRequestClass().isInstance(this.body)) {
            return (T) this.body;
        } else {
            throw new RuntimeException("Body could not be parsed: Class is " + this.body.getClass());
        }
    }

}
