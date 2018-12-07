package com.gemvietnam.timekeeping.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UserUtil {
    public static String convertPassWord(String password) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
        {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1)
            {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return String.valueOf(hexString);

    }

    public static String createToken()
    {
        String key = System.currentTimeMillis() + "/" + Math.random();
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            final StringBuilder builder = new StringBuilder();
            for (byte b : md.digest(key.getBytes()))
            {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();

        }
        catch (Exception e)
        {

        }
        return null;
    }
}
