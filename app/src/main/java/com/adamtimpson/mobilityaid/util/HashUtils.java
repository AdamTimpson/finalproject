package com.adamtimpson.mobilityaid.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class HashUtils {

    private static final String ALGORITHM = "AES";

    private static KeyGenerator keyGen;
    private static SecretKey key;
    private static Cipher eCipher;
    private static Cipher dCipher;

    /**
     * Used to encrypt a password
     * @param s String to encrypt
     * @return Encrypted String
     */
    public static String encrypt(String s) {
        return "";
    }

    /**
     * Mainly for testing purposes. Should not be used in release
     * @param s String to be decrypted
     * @return Decrypted String
     */
    public static String decrypt(String s) {
        return "";
    }

}
