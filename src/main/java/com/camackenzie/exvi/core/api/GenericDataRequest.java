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
public class GenericDataRequest<T> {

    private final Class requestClass;
    private final T body;

    public GenericDataRequest(T body) {
        this.requestClass = body.getClass();
        this.body = body;
    }

    public T getBody() {
        return this.body;
    }

    public Class getRequestClass() {
        return this.requestClass;
    }

}
