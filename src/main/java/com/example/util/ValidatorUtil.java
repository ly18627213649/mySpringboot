package com.example.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 *
 * @author liyang
 * @since 2019/11/12 17:27
 */
public class ValidatorUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9]{6,10}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{8,20}$";
    /**
     * 正则表达式：验证资金密码
     */
    public static final String REGEX_FUND_PASSWORD = "^[0-9]{6}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD_LEVEL = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";


    /**
     * 正则表达式：验证短信验证码
     */
    public static final String REGEX_SMS = "^[0-9]{6}$";

    /**
     * 正则表达式：验证图片验证码
     */
    public static final String REGEX_CAPTCHA = "^[0-9]{4}$";

    /**
     * 正则表达式：验证手机号最新(166，198，199，147)
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|166|(18[0-9])|(19[8|9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]+";
    /**
     * 正则表达式：验证名字
     */
    public static final String REGEX_NAME = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        boolean result = Pattern.matches(REGEX_PASSWORD, password);
        if (result) {
            try {
                Long.parseLong(password);
            } catch (NumberFormatException e) {
                return true;//密码不能为纯数字
            }
        }
        return false;
    }

    /**
     * 验证密码
     * <p>
     * 最小要8位以上，包含字母与数字
     *
     * @author liyang
     * @since 2019/8/29 14:16
     */
    public static boolean checkPassword(String password) {
        Pattern p = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher m = p.matcher(password);
        return m.find();
    }

    /**
     * 方法二：推荐，速度最快
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPasswordLevel(String password) {
        boolean result = Pattern.matches(REGEX_PASSWORD_LEVEL, password);
        if (result) {
            try {
                Long.parseLong(password);
            } catch (NumberFormatException e) {
                return true;//密码不能为纯数字
            }
        }

        return false;
    }

    /**
     * 校验资金密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isFundPasswordLevel(String password) {
        return Pattern.matches(REGEX_FUND_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验姓名
     *
     * @param name
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isName(String name) {
        return Pattern.matches(REGEX_NAME, name);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 验证日期格式 YYYY-mm-dd hh:mi:ss
     *
     * @param time
     * @return
     */
    public static boolean isTime(String time) {
        String formatTime = time.replace(":", "");
        if (time.length() - 2 != formatTime.length()) {
            return false;
        }
        formatTime = formatTime.replace(" ", "");
        if (time.length() - 3 != formatTime.length()) {
            return false;
        }
        formatTime = formatTime.replace("-", "");
        if (time.length() - 5 != formatTime.length()) {
            return false;
        }

        if (formatTime.length() == 14 && StringUtils.isNumeric(formatTime)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 匹配Luhn算法：可用于检测银行卡卡号
     *
     * @param cardNo
     * @return
     */
    public static boolean isBankNo(String cardNo) {
        int[] cardNoArr = new int[cardNo.length()];
        for (int i = 0; i < cardNo.length(); i++) {
            cardNoArr[i] = Integer.valueOf(String.valueOf(cardNo.charAt(i)));
        }
        for (int i = cardNoArr.length - 2; i >= 0; i -= 2) {
            cardNoArr[i] <<= 1;
            cardNoArr[i] = cardNoArr[i] / 10 + cardNoArr[i] % 10;
        }
        int sum = 0;
        for (int i = 0; i < cardNoArr.length; i++) {
            sum += cardNoArr[i];
        }
        return sum % 10 == 0;
    }

    /**
     * 校验短信验证码
     *
     * @param sms
     * @return
     */
    public static boolean isSMS(String sms) {
        return Pattern.matches(REGEX_SMS, sms);
    }


    /**
     * 校验图片验证码
     *
     * @param captcha
     * @return
     */
    public static boolean isCaptcha(String captcha) {
        return Pattern.matches(REGEX_CAPTCHA, captcha);
    }


    /**
     * 验证是否为金额
     *
     * @param amount
     * @return
     */
    public static boolean isAmount(String amount) {
        try {
            Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;

    }

}
