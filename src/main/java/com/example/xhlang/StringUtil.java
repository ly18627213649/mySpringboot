package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

    protected static Logger log = LoggerFactory.getLogger(StringUtil.class);

    public StringUtil() {
    }

    public static String capitalize(String sourceStr) {
        sourceStr = CommUtil.null2String(sourceStr);
        if (sourceStr.equals("")) {
            return sourceStr;
        } else {
            char[] cs = sourceStr.toCharArray();
            boolean checkEnglishLetter = checkEnglishLetter(sourceStr);
            if (!checkEnglishLetter) {
                return sourceStr;
            } else {
                boolean checkCapitalize = checkCapitalize(sourceStr);
                if (checkCapitalize) {
                    return sourceStr;
                } else {
                    cs[0] = (char) (cs[0] - 32);
                    return CommUtil.null2String(new String(cs));
                }
            }
        }
    }

    public static String capitalize(String sourceStr, int position) {
        sourceStr = CommUtil.null2String(sourceStr);
        if (sourceStr.equals("")) {
            return sourceStr;
        } else if (sourceStr.length() < position) {
            return sourceStr;
        } else {
            char[] cs = sourceStr.toCharArray();
            boolean checkEnglishLetter = checkEnglishLetter(sourceStr, position);
            if (!checkEnglishLetter) {
                return sourceStr;
            } else {
                boolean checkUnCapitalize = checkUnCapitalize(sourceStr, position);
                if (checkUnCapitalize) {
                    return sourceStr;
                } else {
                    cs[position - 1] = (char) (cs[position - 1] - 32);
                    return CommUtil.null2String(new String(cs));
                }
            }
        }
    }

    public static String unCapitalize(String sourceStr) {
        sourceStr = CommUtil.null2String(sourceStr);
        if (sourceStr.equals("")) {
            return sourceStr;
        } else {
            char[] cs = sourceStr.toCharArray();
            boolean checkEnglishLetter = checkEnglishLetter(sourceStr);
            if (!checkEnglishLetter) {
                return sourceStr;
            } else {
                boolean checkUnCapitalize = checkUnCapitalize(sourceStr);
                if (checkUnCapitalize) {
                    return sourceStr;
                } else {
                    cs[0] = (char) (cs[0] + 32);
                    return CommUtil.null2String(new String(cs));
                }
            }
        }
    }

    public static String unCapitalize(String sourceStr, int position) {
        sourceStr = CommUtil.null2String(sourceStr);
        if (sourceStr.equals("")) {
            return sourceStr;
        } else if (sourceStr.length() < position) {
            return sourceStr;
        } else {
            char[] cs = sourceStr.toCharArray();
            boolean checkEnglishLetter = checkEnglishLetter(sourceStr, position);
            if (!checkEnglishLetter) {
                return sourceStr;
            } else {
                boolean unCheckCapitalize = checkUnCapitalize(sourceStr, position);
                if (unCheckCapitalize) {
                    return sourceStr;
                } else {
                    cs[position - 1] = (char) (cs[position - 1] + 32);
                    return CommUtil.null2String(new String(cs));
                }
            }
        }
    }

    public static boolean checkEnglishLetter(String sourceStr) {
        return checkEnglishLetter(sourceStr, 1);
    }

    public static boolean checkEnglishLetter(String sourceStr, int position) {
        boolean returnValue = false;
        if (position <= 0) {
            return returnValue;
        } else {
            --position;
            char c = sourceStr.charAt(position);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                returnValue = Boolean.TRUE;
            }

            return returnValue;
        }
    }

    public static boolean checkCapitalize(String sourceStr) {
        boolean returnValue = false;
        char c = sourceStr.charAt(0);
        if (c >= 'A' && c <= 'Z') {
            returnValue = Boolean.TRUE;
        }

        return returnValue;
    }

    public static boolean checkCapitalize(String sourceStr, int position) {
        boolean returnValue = false;
        if (position <= 0) {
            return returnValue;
        } else {
            --position;
            char c = sourceStr.charAt(position);
            if (c >= 'A' && c <= 'Z') {
                returnValue = Boolean.TRUE;
            }

            return returnValue;
        }
    }

    public static boolean checkUnCapitalize(String sourceStr) {
        boolean returnValue = false;
        char c = sourceStr.charAt(0);
        if (c >= 'a' && c <= 'z') {
            returnValue = Boolean.TRUE;
        }

        return returnValue;
    }

    public static boolean checkUnCapitalize(String sourceStr, int position) {
        boolean returnValue = false;
        if (position <= 0) {
            return returnValue;
        } else {
            --position;
            char c = sourceStr.charAt(position);
            if (c >= 'a' && c <= 'z') {
                returnValue = Boolean.TRUE;
            }

            return returnValue;
        }
    }

    public static void main(String[] args) {
        String test = capitalize("fdsafds!fsda", 8);
        System.out.println(test);
    }
}
