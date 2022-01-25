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
public class CachedKey implements Key {

    private final String algorithm, format;
    private final byte[] encoded;

    public CachedKey(Key key) {
        this.algorithm = key.getAlgorithm();
        this.encoded = key.getEncoded();
        this.format = key.getFormat();
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public byte[] getEncoded() {
        return this.encoded;
    }

}
