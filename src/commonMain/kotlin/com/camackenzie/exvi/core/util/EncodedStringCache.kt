/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

/**
 *
 * @author callum
 */
public final class EncodedStringCache {

    private String string;
    private transient String cache;

    public EncodedStringCache(String val) {
        this.set(val);
    }

    public String get() {
        if (this.cache != null) {
            return this.cache;
        }
        return this.cache = CryptographyUtils.decodeString(this.string);
    }

    public void set(String val) {
        this.cache = null;
        this.string = CryptographyUtils.encodeString(val);
    }

}
