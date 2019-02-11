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

    public static String encrypt(String s) {
        String encrypted = "";

        try {
            keyGen = KeyGenerator.getInstance(ALGORITHM);
            key = keyGen.generateKey();

            eCipher = Cipher.getInstance(ALGORITHM);
            eCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] clearText = s.getBytes();
            byte[] cipherText = eCipher.doFinal(clearText);

            encrypted = new String(cipherText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Log.d("HASH ERROR", e.getMessage());
        }

        return encrypted;
    }

    public static String decrypt(String s) {
        String decrypted = "";

        try {
            dCipher = Cipher.getInstance(ALGORITHM);
            dCipher.init(Cipher.DECRYPT_MODE, key);

            byte[] clearText = dCipher.doFinal(s.getBytes());

            decrypted = new String(clearText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            Log.d("HASH ERROR", e.getMessage());
        }

        return decrypted;
    }

}
