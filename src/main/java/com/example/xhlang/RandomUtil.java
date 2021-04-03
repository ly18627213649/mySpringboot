package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {

    private static Logger log = LoggerFactory.getLogger(RandomUtil.class);

    public RandomUtil() {
    }

    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static String getFixedLengthNumber(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < length; ++i) {
            sb.append("0123456789".charAt(random.nextInt("0123456789".length())));
        }

        return sb.toString();
    }

    public static String getFixedLengthRandomMixStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < length; ++i) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }

        return sb.toString();
    }

    public static String getFixedLengthRandomStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < length; ++i) {
            sb.append("abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }

        return sb.toString();
    }

    public static String getFixedLengthRandomLowerCaseStr(int length) {
        return getFixedLengthRandomStr(length).toLowerCase();
    }

    public static String getFixedLengthRandomUpperCaseStr(int length) {
        return getFixedLengthRandomStr(length).toUpperCase();
    }

    public static String getFixedLengthZeroStr(int length) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            sb.append('0');
        }

        return sb.toString();
    }

    public static String getFixedLengthRandomStrSupplyZero(int num, int fixLength) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixLength - strNum.length() >= 0) {
            sb.append(getFixedLengthZeroStr(fixLength - strNum.length()));
            sb.append(strNum);
            return sb.toString();
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixLength + "的字符串发生异常！");
        }
    }

    public static int getNotSimpleRandomStr(int[] param, int len) {
        Random rand = new Random();

        int result;
        int i;
        for (result = param.length; result > 1; --result) {
            i = rand.nextInt(result);
            int tmp = param[i];
            param[i] = param[result - 1];
            param[result - 1] = tmp;
        }

        result = 0;

        for (i = 0; i < len; ++i) {
            result = result * 10 + param[i];
        }

        return result;
    }

    public static <T> T getRandomItemFromPool(T[] param) {
        int index = getRandomInt(0, param.length);
        return param[index];
    }

    private static String getStrMultiplication(String str, int multiplication) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < multiplication; ++i) {
            buffer.append(str);
        }

        return buffer.toString();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getNoSymbolUUID() {
        String uuid = getUUID();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }
}
