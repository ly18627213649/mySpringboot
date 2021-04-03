package com.example.util;

import java.security.MessageDigest;

/**
 * MD5 工具类
 *
 * @author ly
 * @since 2019/9/6
 */
public class MD5Util {

    /**
     * md5 加密  32位结果
     *
     * @author ly
     * @since 2019/9/6
     */
    public static String md5(String encryptStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptStr = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptStr;
    }
}
