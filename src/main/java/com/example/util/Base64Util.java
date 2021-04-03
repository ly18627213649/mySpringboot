package com.example.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Base64 工具类
 *
 * @author ly
 * @since 2019/9/6
 */
public class Base64Util {

    /**
     * Base64 加密
     *
     * @author ly
     * @since 2019/9/6
     */
    public static String base64Encoder(String encoderStr) {

        byte[] bytes = null;
        String str = null;
        try {
            bytes = encoderStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (bytes != null) {
            str = new BASE64Encoder().encode(bytes);
        }

        return str;
    }

    /**
     * Base64 解密
     *
     * @author ly
     * @since 2019/9/6
     */
    public static String base64Decode(String decodeStr) {
        byte[] bytes = null;
        String result = null;

        if (decodeStr != null) {

            BASE64Decoder decoder = new BASE64Decoder();
            try {
                bytes = decoder.decodeBuffer(decodeStr);
                result = new String(bytes, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
