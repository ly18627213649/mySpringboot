package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @ClassName AESUtil
 * @Description AES是一种对称加密算法
 * @Auther bioFish
 * @Date 2019/9/20 13:57
 * @Version v1.0
 **/
public class AESUtil {

    // 16位秘钥
    private static final String sKey = "PAiQdP08utzssVQm";

    private static final Logger log = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 加密
     * @param sSrc 内容
     * @param sKey 秘钥,16位
     * @return
     */
    public static String encrypt(String sSrc,String sKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = sSrc.getBytes();
            int length = dataBytes.length;
            //计算需填充长度
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            //填充
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(sKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryped = cipher.doFinal(plaintext);

            return Base64.getEncoder().encodeToString(encryped);//此处使用BASE64做转码功能，同时能起到2次加密的作用。

        } catch (Exception e) {
            log.error("AESUtil加密失败:" + e.getMessage(), e);
        }
        return null;
    }


    /**
     * 解密
     * @param sSrc 内容
     * @param sKey 秘钥, 16位
     * @return
     */
    public static String decrypt(String sSrc,String sKey) {
        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;

        } catch (Exception e) {
            log.error("AESUtil解密失败:" + e.getMessage(), e);
            return null;
        }
    }
}
