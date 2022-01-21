/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import org.junit.BeforeClass;

/**
 *
 * @author callum
 */
public class CryptographyUtilsTest {

    public CryptographyUtilsTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * Test of encrypt method, of class CryptographyUtils.
     */
    @org.junit.Test
    public void testEncrypt() throws Exception {
    }

    /**
     * Test of decrypt method, of class CryptographyUtils.
     */
    @org.junit.Test
    public void testDecrypt() throws Exception {
    }

    /**
     * Test of encryptFailOnError method, of class CryptographyUtils.
     */
    @org.junit.Test
    public void testEncryptFailOnError() {
        CryptographyUtils.encryptFailOnError("Hello encryption world!",
                "AES", "AES");
    }

    /**
     * Test of decryptFailOnError method, of class CryptographyUtils.
     */
    @org.junit.Test
    public void testEncryptDecrypt() {
        String in = "This text was encrypted and decrypted.";
        EncryptionResult er = CryptographyUtils.encryptFailOnError(
                in, "AES", "AES");
        String original = CryptographyUtils.decryptFailOnError(er, "AES");

        assert (original.equals(in));
    }

}
