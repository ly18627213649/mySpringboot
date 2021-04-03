package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常见工具
 *
 * @author liyang
 * @since 2019/10/11 11:04
 */
public class CommUtil {

    private static Logger log = LoggerFactory.getLogger(CommUtil.class);

    public CommUtil() {
    }

    public static boolean isNull(String source) {
        return null2String(source).equals("");
    }

    public static String null2String(String source) {
        return source == null ? "" : source;
    }

    public static String null2String(Object source) {
        return source == null ? "" : source.toString();
    }

    public static String null2String(String source, String defaultValue) {
        return source == null ? defaultValue : source;
    }

    public static String null2String(Object source, String defaultValue) {
        return source == null ? defaultValue : source.toString();
    }

    public static boolean null2Boolean(String source) {
        boolean returnValue = Boolean.FALSE;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Boolean.parseBoolean(source);
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static boolean null2Boolean(Object source) {
        boolean returnValue = Boolean.FALSE;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Boolean.parseBoolean(source.toString());
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static int null2Int(String source) {
        int returnValue = 0;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Integer.parseInt(source);
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static int null2Int(Object source) {
        int returnValue = 0;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Integer.parseInt(source.toString());
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static short null2Short(String source) {
        short returnValue = 0;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Short.parseShort(source);
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static short null2Short(Object source) {
        short returnValue = 0;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Short.parseShort(source.toString());
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static long null2Long(String source) {
        long returnValue = 0L;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Long.parseLong(source);
            } catch (Exception var4) {
                log.error(String.format("将数据进行Null处理时出错：%s", var4));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static long null2Long(Object source) {
        long returnValue = 0L;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Long.parseLong(source.toString());
            } catch (Exception var4) {
                log.error(String.format("将数据进行Null处理时出错：%s", var4));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static double null2Double(String source) {
        double returnValue = 0.0D;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Double.parseDouble(source);
            } catch (Exception var4) {
                log.error(String.format("将数据进行Null处理时出错：%s", var4));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static double null2Double(Object source) {
        double returnValue = 0.0D;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Double.parseDouble(source.toString());
            } catch (Exception var4) {
                log.error(String.format("将数据进行Null处理时出错：%s", var4));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static float null2Float(String source) {
        float returnValue = 0.0F;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Float.parseFloat(source);
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static float null2Float(Object source) {
        float returnValue = 0.0F;
        if (source != null && !source.equals("")) {
            try {
                returnValue = Float.parseFloat(source.toString());
            } catch (Exception var3) {
                log.error(String.format("将数据进行Null处理时出错：%s", var3));
            }

            return returnValue;
        } else {
            return returnValue;
        }
    }

    public static List<String> string2List(String source) {
        return string2List(source, ",");
    }

    public static List<String> string2List(String source, String split) {
        List<String> returnValue = new ArrayList();
        if (source != null && !source.isEmpty()) {
            String[] _split = source.split(split);
            if (_split.length <= 0) {
                return returnValue;
            } else {
                returnValue = Arrays.asList(_split);
                return returnValue;
            }
        } else {
            return returnValue;
        }
    }

    public static boolean hasSpecialChar(String sourceStr) {
        Pattern p = Pattern.compile("[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t");
        Matcher m = p.matcher(sourceStr);
        return m.find();
    }

    public static boolean hasSpecialChar(String sourceStr, String regEx) {
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(sourceStr);
        return m.find();
    }

    public static boolean hasChineseChar(String sourceStr) {
        Pattern p = Pattern.compile("[\\u4E00-\\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(sourceStr);
        return m.find();
    }

    public static boolean isChinese(char chars) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(chars);
        return Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS == ub || Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS == ub || Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS == ub || Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT == ub || Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub || Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub;
    }

    public static boolean hasEnglishChar(String sourceStr) {
        Pattern p = Pattern.compile("[a-zA-Z|\\!|\\,|\\.|\\(|\\)|\\<|\\>|\\?|\\:|\\;|\\[|\\]]");
        Matcher m = p.matcher(sourceStr);
        return m.find();
    }

    public static boolean isNumeric(String sourceStr) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(sourceStr);
        return m.find();
    }

    public static boolean isFixedLengthNumeric(String sourceStr, int minLength, int maxLength) {
        if (minLength <= 0) {
            return isNumeric(sourceStr);
        } else {
            int length = sourceStr.length();
            if (length >= minLength && length <= maxLength) {
                Pattern p = Pattern.compile("^[0-9]*$");
                Matcher m = p.matcher(sourceStr);
                return m.find();
            } else {
                return false;
            }
        }
    }

    public static boolean checkEmail(String email) {
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.find();
    }

    public static boolean checkPhone(String phone) {
        boolean returnValue = false;
        returnValue = check10086(phone);
        if (returnValue) {
            return returnValue;
        } else {
            returnValue = check10010(phone);
            if (returnValue) {
                return returnValue;
            } else {
                returnValue = check10000(phone);
                if (returnValue) {
                    return returnValue;
                } else {
                    returnValue = checkVirtualMobile(phone);
                    return returnValue ? returnValue : returnValue;
                }
            }
        }
    }

    public static boolean checkPhone(String phone, boolean checkVirtualMobile) {
        boolean returnValue = check10086(phone);
        if (returnValue) {
            return returnValue;
        } else {
            returnValue = check10010(phone);
            if (returnValue) {
                return returnValue;
            } else {
                returnValue = check10000(phone);
                if (returnValue) {
                    return returnValue;
                } else {
                    if (checkVirtualMobile) {
                        returnValue = checkVirtualMobile(phone);
                        if (returnValue) {
                            return returnValue;
                        }
                    }

                    return returnValue;
                }
            }
        }
    }

    public static boolean check10086(String phone) {
        Pattern p = Pattern.compile("^((13[4-9])|(14[1,7])|(15[0-2,7-9])|(178)|(198)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$");
        Matcher m = p.matcher(phone);
        return m.find();
    }

    public static boolean check10010(String phone) {
        Pattern p = Pattern.compile("^((13[0-2])|(145)|(166)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$");
        Matcher m = p.matcher(phone);
        return m.find();
    }

    public static boolean check10000(String phone) {
        Pattern p = Pattern.compile("^((133)|(153)|(17[3,7,9])|(19[1,9])|(18[0,1,9])|(149))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.find();
    }

    public static boolean checkVirtualMobile(String phone) {
        Pattern p = Pattern.compile("^((170))\\d{8}|(1718)|(1719)\\d{7}$");
        Matcher m = p.matcher(phone);
        return m.find();
    }

    public static boolean isMatch(String sourceStr, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(sourceStr);
        return m.matches();
    }

    public static boolean checkLat(double latitude) {
        Pattern p = Pattern.compile("((?:[0-9]|[1-8][0-9])\\.([0-9]{0,6}))|((?:90)\\.([0]{0,6}))");
        Matcher m = p.matcher(null2String((Object) latitude));
        return m.matches();
    }

    public static boolean checkLon(double longitude) {
        Pattern p = Pattern.compile("((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,6}))|((?:180)\\.([0]{0,6}))");
        Matcher m = p.matcher(null2String((Object) longitude));
        return m.matches();
    }
}
