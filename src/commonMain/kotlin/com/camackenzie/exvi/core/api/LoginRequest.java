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
public class LoginRequest {

    private final EncodedStringCache username,
            passwordHash;

    public LoginRequest(String username, String passwordHash) {
        this.username = new EncodedStringCache(username);
        this.passwordHash = new EncodedStringCache(passwordHash);
    }

    public String getUsername() {
        return this.username.get();
    }

    public String getPasswordHash() {
        return this.passwordHash.get();
    }

}
