/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import java.security.Key;

/**
 *
 * @author callum
 */
public class EncryptionResult {

    private final String encrypted;
    private final Key key;

    public EncryptionResult(String enc, Key key) {
        this.encrypted = enc;
        this.key = key;
    }

    public String getEncrypted() {
        return this.encrypted;
    }

    public Key getKey() {
        return this.key;
    }

}
