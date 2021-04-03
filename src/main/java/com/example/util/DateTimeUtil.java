package com.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateTimeUtil {

    protected static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public final static SimpleDateFormat[] defaultDateFormats = new SimpleDateFormat[]{
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("yyyyMMdd"),
            new SimpleDateFormat("yyyy/MM/dd"),
            new SimpleDateFormat("yyyyMMddHHmmssSSS"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("HH:mm:ss")
    };


    public final static SimpleDateFormat defaultDataFormat = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 字符串转成日期
     *
     * @param dateStr 日期字符串
     * @param format  格式
     * @return
     * @throws ParseException
     */
    public static Date parseToDate(String dateStr, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 字符串转成日期
     *
     * @param dateStr 日期字符串
     * @return
     * @throws ParseException
     */
    public static Date parsetToDate(String dateStr) throws ParseException {
        Date date = null;
        for (SimpleDateFormat defaultFormat : defaultDateFormats) {
            try {
                date = defaultFormat.parse(dateStr);
                break;
            } catch (ParseException e) {
                continue;
            }
        }
        if (date == null) {
            throw new ParseException("日期格式转换错误!", 0);
        }
        return date;
    }


    /**
     * 获取当前时间的timestamp对象
     */
    public static Timestamp getNowTimeStamp() {
        return new Timestamp((new Date().getTime()));
    }


    /**
     * 日期格式化字符串
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String format(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /**
     * 默认日期格式化
     *
     * @param date
     * @return
     */
    public static String defaultFormat(Object date) {

        return defaultDataFormat.format(date);
    }

    /**
     * 判断日期格式是否合法
     *
     * @param inputStr 输入字符
     * @param format   格式限制
     */
    public static boolean isValidDate(String inputStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return isValidDate(inputStr, dateFormat);
    }

    /**
     * 判断日期格式是否合法
     *
     * @param inputStr 输入字符
     * @param format   格式限制
     */
    public static boolean isValidDate(String inputStr, SimpleDateFormat format) {
        format.setLenient(false);
        try {
            format.parse(inputStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static Integer daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串的日期格式的计算
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     */
    public static Integer daysBetween(String smdate, String bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 某年某月的第一天
     *
     * @param year
     * @param month
     * @return
     * @throws ParseException
     */
    public static Date firstDate(int year, int month) {

        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return firstDate;

    }

    /**
     * 某年某月的第最后一天
     *
     * @param year
     * @param month
     */
    public static Date lastDate(int year, int month) {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.MONTH, month - 1);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);

        Date lastDate = cal.getTime();
        return lastDate;

    }

    /**
     * 两个日期之间的月份
     *
     * @param minDate
     * @param maxDate
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
//            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 1);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }


    /**
     * 两个日期之间的月份,如果有移除时间，则需要所有包括月份
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetweenHaveRemove(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
//            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 1);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 计算当月的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取当月的天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        return a.getActualMaximum(Calendar.DATE);
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;

        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }

    /**
     * 日期加1天
     */
    public static String addOneday(String today) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = new Date(f.parse(today).getTime() + 24 * 3600 * 1000);
            return f.format(d);
        } catch (Exception ex) {
            return "输入格式错误";
        }
    }


    /**
     * 时间减1小时
     */
    public static String reduceOneHour(String daytime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = new Date(f.parse(daytime).getTime() - 3600 * 1000);
            return f.format(d);
        } catch (Exception ex) {
            return "输入格式错误";
        }
    }


    /**
     * 日期加1天
     */
    public static Date addOneday(Date day) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(day.getTime() + 24 * 3600 * 1000);
        return d;
    }

    /**
     * 日期加N月
     */
    public static Date addMonths(Date day, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(day);
        calendar.add(calendar.MONTH, month);//把日期往后增加一个月.整数往后推,负数往前移动
        Date d = calendar.getTime();
        return d;
    }

    /**
     * 计算当月的最后一天
     */
    public static String lastDayOfMonthStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return defaultDataFormat.format(cal.getTime());
    }


    /**
     * 计算当月的第一天
     */
    public static String firstDayOfMonthStr(Date date) {

        // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        cale.setTime(date);
        cale.set(Calendar.MONTH, 0);
        cale.roll(Calendar.DAY_OF_MONTH, 1);
        String firstday = defaultDataFormat.format(cale.getTime());
        return firstday;
    }


    /**
     * 判断2个时间的大小
     */
    public static boolean timeBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        return time2 - time1 >= 0 ? true : false;
    }

    /**
     * 判断2个时间的大小
     */
    public static boolean dayBetween(Date smdate, Date bdate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        return time2 - time1 >= 0 ? true : false;
    }


    /**
     * 日期加上相应的天数
     *
     * @throws ParseException
     */
    public static Date adddays(Date day, Integer days) throws ParseException {

        Calendar cl = Calendar.getInstance();
        cl.setTime(day);
        cl.add(Calendar.DATE, days);
        return cl.getTime();

    }

    /**
     * 产生3位随机数
     *
     * @return
     */
    public static String createRandomNum() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    /**
     * yyyyMMddHHmmssSSS+产生3位随机数
     *
     * @return
     */
    public static String createNormalCode() {
        return defaultDateFormats[3].format(new Date()) + createRandomNum();
    }


    /**
     * 将当前时间转为long数字串
     *
     * @return
     */
    public static long getLongTime() {
        Date mydate = new Date();
        return mydate.getTime();
    }

    /**
     * 将输入时间转为long数字串
     *
     * @return
     * @throws ParseException
     */
    public static long getStrLongTime(String datetime) throws ParseException {

        return defaultDateFormats[2].parse(datetime).getTime();

    }

    /**
     * 将输入数字串转为时间
     */
    public static Date long2date(long timestamp) {
        Date mydate = new Date();
        mydate.setTime(timestamp);
        return mydate;
    }


}
