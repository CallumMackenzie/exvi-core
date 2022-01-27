/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.camackenzie.exvi.core.util;

import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.function.Function;
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
    private static final Gson gson = new Gson();

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

    public static String encodeStringToBase64(String in) {
        return CryptographyUtils
                .bytesToBase64String(in.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeStringFromBase64(String in) {
        return new String(CryptographyUtils.bytesFromBase64String(in),
                StandardCharsets.UTF_8);
    }

    public static String applyRotationCipher(String in, int rotation) {
        return CryptographyUtils.applyDynamicRotationCipher(in, i -> rotation);
    }

    public static String revertRotationCipher(String in, int rot) {
        return CryptographyUtils.applyRotationCipher(in, -rot);
    }

    public static String applyDynamicRotationCipher(String in, Function<Integer, Integer> fn) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < in.length(); ++i) {
            ret.append((char) (in.charAt(i) + fn.apply(i)));
        }
        return ret.toString();
    }

    public static String revertDynamicRotationCipher(String in, Function<Integer, Integer> fn) {
        return CryptographyUtils.applyDynamicRotationCipher(in, i -> -fn.apply(i));
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

    public static String encodeString(String in) {
        EncryptionResult er = CryptographyUtils.encryptAES(in);
        String s0 = gson.toJson(er);
        String s1 = CryptographyUtils.applyDynamicRotationCipher(s0, i -> i % 12);
        String s2 = CryptographyUtils.encodeStringToBase64(s1);
        String s3 = CryptographyUtils.applyDynamicRotationCipher(s2, i -> (int) ((i / (double) s2.length()) * 10));
        String s4 = CryptographyUtils.encodeStringToBase64(s3);
        String s5 = CryptographyUtils.applyRotationCipher(s4, 1);
        return s5;
    }

    public static String decodeString(String s5) {
        String s4 = CryptographyUtils.revertRotationCipher(s5, 1);
        String s3 = CryptographyUtils.decodeStringFromBase64(s4);
        String s2 = CryptographyUtils.revertDynamicRotationCipher(s3, i -> (int) ((i / (double) s3.length()) * 10));
        String s1 = CryptographyUtils.decodeStringFromBase64(s2);
        String s0 = CryptographyUtils.revertDynamicRotationCipher(s1, i -> i % 12);
        EncryptionResult er = gson.fromJson(s0, EncryptionResult.class);
        String in = CryptographyUtils.decryptAES(er);
        return in;
    }

}
