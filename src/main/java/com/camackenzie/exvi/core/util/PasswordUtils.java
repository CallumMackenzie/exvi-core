/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 *
 * @author callum
 */
public class PasswordUtils {

    private static final Random random = new Random();

    public static String generateSalt(int nBytes, Random random) {
        assert (nBytes >= 0);

        byte[] randBytes = new byte[nBytes];
        random.nextBytes(randBytes);
        return PasswordUtils.bytesToBase64String(randBytes);
    }

    public static String generateSalt(int nBytes) {
        return PasswordUtils.generateSalt(nBytes, PasswordUtils.random);
    }

    public static String generateSalt() {
        return PasswordUtils.generateSalt(
                (int) (PasswordUtils.random.nextDouble() * 10 + 6) << 1);
    }

    public static String hashSHA256(String in) {
        return PasswordUtils.hashWithAlgorithm(in, "SHA-256");
    }

    public static String hashWithAlgorithm(String in, String algo) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algo);
            byte[] byteHash = digest.digest(in.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(byteHash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String bytesToBase64String(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

}
