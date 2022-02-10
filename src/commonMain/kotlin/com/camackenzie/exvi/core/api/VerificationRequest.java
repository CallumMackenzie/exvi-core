/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.api;

import com.camackenzie.exvi.core.util.EncodedStringCache;

/**
 *
 * @author callum
 */
public class VerificationRequest {

    private final EncodedStringCache username, email, phone;

    public VerificationRequest(String username,
            String email,
            String phone) {
        this.username = new EncodedStringCache(username);
        this.email = new EncodedStringCache(email);
        this.phone = new EncodedStringCache(phone);
    }

    public String getUsername() {
        return username.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPhone() {
        return phone.get();
    }

}
