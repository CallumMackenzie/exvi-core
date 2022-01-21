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
public class AccountCreationRequest {

    private final String username,
            verificationCode,
            password;

    public AccountCreationRequest(String username,
            String verificationCode,
            String password) {
        this.username = username;
        this.verificationCode = verificationCode;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getPassword() {
        return password;
    }

}
