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
public class VerificationResult {

    private final int error;
    private final String message;

    public VerificationResult(int err, String msg) {
        this.error = err;
        this.message = msg;
    }

    public int getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }
}
