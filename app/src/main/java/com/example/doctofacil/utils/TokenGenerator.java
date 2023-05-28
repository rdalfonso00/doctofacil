package com.example.doctofacil.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenGenerator {
    private static final String SECRET_KEY = "your_secret_key";

    public static String generateAuthToken(int userId) {
        String tokenData = userId + SECRET_KEY;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(tokenData.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception accordingly
            e.printStackTrace();
            return null;
        }
    }
}

