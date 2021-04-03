package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SignUtil {

    protected static Logger log = LoggerFactory.getLogger(SignUtil.class);
    private static final List<String> ExcludeFields = Arrays.asList("Sign", "SignType");

    public SignUtil() {
    }

    public static String toSign(Map<String, String> params, String privateKey) {
        return toSign(params, privateKey, ExcludeFields);
    }

    public static String toSign(Map<String, String> params, String privateKey, List<String> excludeFieldList) {
        String returnValue = "";
        if (params != null && !params.isEmpty()) {
            params = paramsFilter(params, excludeFieldList);
            String paramLink = getParamLink(params);
            paramLink.concat(privateKey);
            String signType = CommUtil.null2String((String) params.get("SignType"), "MD5");
            byte var8 = -1;
            switch (signType.hashCode()) {
                case -1523887726:
                    if (signType.equals("SHA-256")) {
                        var8 = 1;
                    }
                    break;
                case -1523884971:
                    if (signType.equals("SHA-512")) {
                        var8 = 2;
                    }
                    break;
                case 78861104:
                    if (signType.equals("SHA-1")) {
                        var8 = 0;
                    }
            }

            switch (var8) {
                case 0:
                case 1:
                case 2:
                default:
                    return returnValue;
            }
        } else {
            return returnValue;
        }
    }

    public static Map<String, String> paramsFilter(Map<String, String> params, List<String> excludeFieldList) {
        if (params != null && !params.isEmpty()) {
            Map<String, String> returnValue = new HashMap();
            Iterator var3 = params.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var3.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (excludeFieldList.contains(key)) {
                    log.debug(String.format("[%s]为排除字段，不进行处理！", key));
                } else if (CommUtil.null2String(value).equals("")) {
                    log.debug(String.format("字段[%s]的值为空，不进行处理！", key));
                } else {
                    returnValue.put(key, value);
                }
            }

            return returnValue;
        } else {
            return params;
        }
    }

    private static String getParamLink(Map<String, String> params) {
        String returnValue = "";
        if (params != null && !params.isEmpty()) {
            TreeMap<String, String> treeMap = new TreeMap(params);
            StringBuffer buffer = new StringBuffer();
            Iterator var4 = treeMap.entrySet().iterator();

            while (var4.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var4.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                buffer.append("&");
                buffer.append(key);
                buffer.append("=");
                buffer.append(value);
            }

            returnValue = buffer.toString();
            returnValue = returnValue.substring(returnValue.indexOf("&") + 1);
            return returnValue;
        } else {
            return returnValue;
        }
    }
}
