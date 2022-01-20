/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core;

/**
 *
 * @author callum
 */
public class APIResult<T> {

    private int statusCode;
    private T body;
    private HashMap<String, String> headers;

    public APIResult withJsonHeader() {
    }

    public int getStatusCode() {
    }

    public void setStatusCode(int c) {
    }

    public T getBody() {
    }

    public void setBody(T body) {
    }

    public HashMap<String, String> getHeaders() {
    }

    public void setHeaders(HashMap<String, String> headers) {
    }

}
