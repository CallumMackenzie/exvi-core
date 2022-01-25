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
public class AccountSaltResult extends DataResult<String> {

    public AccountSaltResult(int err,
            String msg, String salt) {
        super(err, msg, salt);
    }

    public AccountSaltResult(int err, String msg) {
        super(err, msg, "");
    }

    public AccountSaltResult(String msg, String salt) {
        super(0, msg, salt);
    }

    public String getSalt() {
        return this.getResult();
    }

}
