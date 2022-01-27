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
public class GenericDataResult<T> extends DataResult<T> {

    private final Class responseClass;

    public GenericDataResult(int code, String msg, Class resClass) {
        super(code, msg, null);
        this.responseClass = resClass;
    }

    public GenericDataResult(String msg, T resp) {
        super(0, msg, resp);
        this.responseClass = resp.getClass();
    }

    public GenericDataResult(T resp) {
        super(0, "Success", resp);
        this.responseClass = resp.getClass();
    }

    public Class getResponseClass() {
        return this.responseClass;
    }

}
