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
public class AccountAccessKeyResult {

    private final int error;
    private final String message;
    private final String accessKey;

    public AccountAccessKeyResult(int error,
            String message,
            String accessKey) {
        this.error = error;
        this.message = message;
        this.accessKey = accessKey;
    }

    public int getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

}
