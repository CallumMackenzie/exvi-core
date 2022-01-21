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
public class AccountAccessKeyResult extends DataResult<String> {

    public AccountAccessKeyResult(int error,
            String message,
            String accessKey) {
        super(error, message, accessKey);
    }

    public AccountAccessKeyResult(String msg, String key) {
        this(0, msg, key);
    }

    public AccountAccessKeyResult(int err, String msg) {
        this(err, msg, "");
    }

    public String getAccessKey() {
        return this.getResult();
    }

}
