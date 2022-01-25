/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author callum
 */
public class CryptographyUtils {

    private static final Random random = new Random();

    public static EncryptionResult encrypt(String in, String algo, String keyAlgo)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algo);
        KeyGenerator keyGen = KeyGenerator.getInstance(keyAlgo);
        keyGen.init(256);
        SecretKey key = keyGen.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(in.getBytes(StandardCharsets.UTF_8));
        String encryptedStr = bytesToBase64String(encrypted);
        return new EncryptionResult(encryptedStr, new CachedKey(key));
    }

    public static String decrypt(EncryptionResult in, String algo)
            throws IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algo);
        cipher.init(Cipher.DECRYPT_MODE, in.getKey().getKey());
        byte[] encrypted = bytesFromBase64String(in.getEncrypted());
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public static EncryptionResult encryptFailOnError(String in,
            String algo,
            String keyAlgo) {
        try {
            return CryptographyUtils.encrypt(in, algo, keyAlgo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptFailOnError(EncryptionResult in, String algo) {
        try {
            return CryptographyUtils.decrypt(in, algo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static EncryptionResult encryptAES(String in) {
        return CryptographyUtils.encryptFailOnError(in, "AES", "AES");
    }

    public static String decryptAES(EncryptionResult er) {
        return CryptographyUtils.decryptFailOnError(er, "AES");
    }

    public static String bytesToBase64String(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    public static String hashSHA256(String in) {
        return CryptographyUtils.hashWithAlgorithm(in, "SHA-256");
    }

    public static byte[] bytesFromBase64String(String encoded) {
        return Base64.getDecoder().decode(encoded);
    }

    public static String generateSalt(int nBytes, Random random) {
        assert (nBytes >= 0);
        byte[] randBytes = new byte[nBytes];
        random.nextBytes(randBytes);
        return CryptographyUtils.bytesToBase64String(randBytes);
    }

    public static String generateSalt(int nBytes) {
        return CryptographyUtils.generateSalt(nBytes, CryptographyUtils.random);
    }

    public static String generateSalt() {
        return CryptographyUtils.generateSalt((int) (CryptographyUtils.random.nextDouble() * 10 + 6) << 1);
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

}
