package com.atguigu.interviewQ2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ThreadLocal 封装 SimpleDateFormat
 * 解决: SimpleDateFormat 作为共享变量时,多线程安全问题
 */
public class ThreadLocalDateUtil {
    private static final String date_format = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public static DateFormat getDateFormat() {
        DateFormat df = threadLocal.get();
        if(df == null) {
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        }
        return df;
    }

    // 添加清理方法,避免ThreadLocal 内存泄露问题
    public static void remove() {
        threadLocal.remove();
    }

    public static String formatDate(Date date) {
        return getDateFormat().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }
}
