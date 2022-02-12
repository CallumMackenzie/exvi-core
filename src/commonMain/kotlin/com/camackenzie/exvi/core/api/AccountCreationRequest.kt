/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

import com.camackenzie.exvi.core.util.CryptographyUtils;
import com.camackenzie.exvi.core.util.EncodedStringCache;

/**
 *
 * @author callum
 */
public class AccountCreationRequest {

    private final EncodedStringCache username,
            verificationCode,
            password;

    public AccountCreationRequest(String username,
            String verificationCode,
            String password) {
        this.username = new EncodedStringCache(username);
        this.verificationCode = new EncodedStringCache(verificationCode);
        this.password = new EncodedStringCache(password);
    }

    public String getUsername() {
        return username.get();
    }

    public String getVerificationCode() {
        return verificationCode.get();
    }

    public String getPassword() {
        return password.get();
    }

}
