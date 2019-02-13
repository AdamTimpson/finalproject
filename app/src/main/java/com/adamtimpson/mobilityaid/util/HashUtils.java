package com.adamtimpson.mobilityaid.util;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
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
     * @return Entrypted String
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
