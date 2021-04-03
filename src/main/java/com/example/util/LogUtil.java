package com.example.util;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author ly
 * @since 2019/9/6
 */
public class LogUtil {

    private Logger logger;

    public static LogUtil getLogger(Class<T> tClass) {

        LogUtil logUtil = new LogUtil();

        logUtil.logger = LoggerFactory.getLogger(tClass);

        return logUtil;
    }

    public static LogUtil getLogger() {

        LogUtil logUtil = new LogUtil();
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements != null && elements.length > 0) {
            logUtil.logger = LoggerFactory.getLogger(elements[elements.length - 1].getClassName());
        } else {
            logUtil.logger = LoggerFactory.getLogger(LogUtil.class);
        }
        return logUtil;
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void error(String msg, Throwable throwable) {

        logger.error(msg, throwable);
    }

    public void info(String msg) {

        logger.info(msg);
    }

}
