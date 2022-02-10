/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author callum
 */
public class CachedKey {

    private final String algorithm, encoded;

    public CachedKey(Key key) {
        this.algorithm = CryptographyUtils.bytesToBase64String(key.getAlgorithm().getBytes());
        this.encoded = CryptographyUtils.bytesToBase64String(key.getEncoded());
    }

    public Key getKey() {
        String algo = new String(CryptographyUtils.bytesFromBase64String(this.algorithm));
        byte[] enc = CryptographyUtils.bytesFromBase64String(this.encoded);
        return new SecretKeySpec(enc, algo);
    }

}
