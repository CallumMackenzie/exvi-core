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
public class RetrieveSaltRequest {

    private final EncodedStringCache username;

    public RetrieveSaltRequest(String username) {
        this.username = new EncodedStringCache(username);
    }

    public String getUsername() {
        return this.username.get();
    }

}
