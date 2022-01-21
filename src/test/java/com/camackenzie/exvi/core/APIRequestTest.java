/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Alexx
 */
public class APIRequestTest {

    public APIRequestTest() {
    }

    /**
     * Test of withJsonHeader method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testWithJsonHeader() {
    }

    /**
     * Test of getBody method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testGetBody() {
    }

    /**
     * Test of setBody method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testSetBody() {
    }

    /**
     * Test of getHeaders method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testGetHeaders() {
    }

    /**
     * Test of setHeaders method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testSetHeaders() {
    }

    /**
     * Test of getEndpoint method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testGetEndpoint() {
    }

    /**
     * Test of setEndpoint method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testSetEndpoint() {
    }

    /**
     * Test of send method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testSend_0args() {
    }

    /**
     * Test of send method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testSend_3args() {
    }

    /**
     * Test of sendJson method, of class APIRequest.
     */
    @org.junit.jupiter.api.Test
    public void testSendJson() throws InterruptedException, ExecutionException {
        // This takes too long to run
//        Future<APIResult<VerificationResult>> apir
//                = APIRequest.sendJson("https://s36irvth41.execute-api.us-east-2.amazonaws.com/test/verification",
//                        new UserVerificationDetails("callum",
//                                "alexxander1611@gmail.com",
//                                "+14034731818"),
//                        VerificationResult.class);
//        System.out.println(apir.get().getBody());

    }

    public class UserVerificationDetails {

        public String username;
        public String email;
        public String phone;

        public UserVerificationDetails(String uname, String em, String phone) {
            this.username = uname;
            this.email = em;
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "Username: " + this.username
                    + ", Email: " + this.email
                    + ", Phone: " + this.phone;
        }

    }

    public class VerificationResult {

        public String message;
        public int error;

        public VerificationResult(int err, String message) {
            this.error = err;
            this.message = message;
        }

        @Override
        public String toString() {
            return "error: " + this.error + ", message: " + this.message;
        }

    }

}
