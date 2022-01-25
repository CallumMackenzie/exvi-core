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
public class RetrieveSaltRequest {

    private String username;

    public RetrieveSaltRequest(String username) {
        this.username = username;
    }

    public String getUsername(String username) {
        return this.username;
    }

}
