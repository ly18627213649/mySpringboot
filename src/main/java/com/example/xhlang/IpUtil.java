package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {

    private static Logger log = LoggerFactory.getLogger(IpUtil.class);

    public IpUtil() {
    }

    public static String getRealIP(HttpServletRequest request) {
        String returnValue = "unknown";
        if (request == null) {
            return returnValue;
        } else {
            returnValue = request.getHeader("X-Forwarded-For");
            if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
                returnValue = request.getHeader("Proxy-Client-IP");
            }

            if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
                returnValue = request.getHeader("WL-Proxy-Client-IP");
            }

            if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
                returnValue = request.getHeader("HTTP_CLIENT_IP");
            }

            if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
                returnValue = request.getHeader("HTTP_X_FORWARDED_FOR");
            }

            if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
                returnValue = request.getHeader("X-Real-IP");
            }

            if (returnValue == null || returnValue.length() == 0 || "unknown".equalsIgnoreCase(returnValue)) {
                returnValue = request.getRemoteAddr();
            }

            return returnValue.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : returnValue;
        }
    }

    public static long ip2Long(String sourceIP) {
        String[] items = sourceIP.split(".");
        return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
    }

    public static String long2Ip(long source) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(source & 255L).append(".");
        buffer.append(source >> 8 & 255L).append(".");
        buffer.append(source >> 16 & 255L).append(".");
        buffer.append(source >> 24 & 255L);
        return buffer.toString();
    }

    public static String getHostIp() {
        String returnValue = "127.0.0.1";

        try {
            returnValue = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var2) {
            log.warn(String.format("取得HOST的IP地址时出错：%s", var2));
        }

        return returnValue;
    }

    public static String getServerIp(String domain) {
        String returnValue = "";

        try {
            InetAddress inetAddress = InetAddress.getByName(domain);
            returnValue = inetAddress.getHostAddress();
        } catch (UnknownHostException var3) {
            log.error(String.format("取得[%s]的服务器IP时出错：%s", domain, var3));
        }

        return returnValue;
    }

    public static String getHostName() {
        String returnValue = "未知";

        try {
            returnValue = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException var2) {
            log.warn(String.format("取得HOSTNAME时出错：%s", var2));
        }

        return returnValue;
    }

    public static boolean iamserver() {
        String domain = "IamServer";
        String serverIp = getServerIp("IamServer");
        boolean returnValue = CommUtil.null2String(serverIp).equalsIgnoreCase("127.0.0.1");
        return returnValue;
    }
}
