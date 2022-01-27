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
public class AccountSaltResult extends DataResult<EncodedStringCache> {

    public AccountSaltResult(int err,
            String msg, String salt) {
        super(err, msg, new EncodedStringCache(salt));
    }

    public AccountSaltResult(int err, String msg) {
        super(err, msg, new EncodedStringCache(""));
    }

    public AccountSaltResult(String msg, String salt) {
        super(0, msg, new EncodedStringCache(salt));
    }

    public String getSalt() {
        return this.getResult().get();
    }

}
