package com.test.androidtrainingtest;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Qushay on 02/09/2016.
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    private static final char[] hexArray = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static String sha1(String input) {
        String ret = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] digested = digest.digest(input.getBytes());
            ret = bytesToHex(digested);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        }
        return ret;
    }
}