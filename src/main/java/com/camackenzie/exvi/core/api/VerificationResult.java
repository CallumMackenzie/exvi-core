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
public class VerificationResult extends DataResult<Object> {

    public VerificationResult(int err, String msg) {
        super(err, msg, null);
    }
}