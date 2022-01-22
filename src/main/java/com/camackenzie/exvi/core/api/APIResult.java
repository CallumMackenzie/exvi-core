/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

import com.google.gson.Gson;
import java.util.HashMap;

/**
 *
 * @author callum
 */
public class APIResult<T> {

    private static Gson gson = new Gson();

    private T body;
    private int statusCode;
    private HashMap<String, String> headers;

    public APIResult(int statusCode, T body, HashMap<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public APIResult(APIResult other, T newBody) {
        this.body = newBody;
        this.statusCode = other.getStatusCode();
        this.headers = other.getHeaders();
    }

    public APIResult<T> withJsonHeader() {
        this.headers.put("content-type", "application/json");
        return this;
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int e) {
        this.statusCode = e;
    }

    public String toJson() {
        this.withJsonHeader();
        return gson.toJson(this);
    }

    public static <T> APIResult<T> jsonResult(int statusCode, T body) {
        return new APIResult(statusCode, body, new HashMap<>()).withJsonHeader();
    }

    public static <T> String jsonString(int statusCode, T body) {
        return APIResult.<T>jsonResult(statusCode, body).toJson();
    }

}
