package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public DateUtil() {
    }

    public static long now() {
        return System.currentTimeMillis();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime, ZoneId zoneId) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public static Date addMillisecond(Date sourceDate, long millisecond) {
        sourceDate.setTime(sourceDate.getTime() + millisecond);
        return sourceDate;
    }

    public static Date addDays(int day) {
        return addDays(new Date(), day);
    }

    public static Date addDays(Date sourceDate, int day) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(5, day);
        return calendar.getTime();
    }

    public static Date addSeconds(int second) {
        return addSeconds(new Date(), second);
    }

    public static Date addSeconds(Date sourceDate, int second) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(13, second);
        return calendar.getTime();
    }

    public static Date addMinutes(int minute) {
        return addMinutes(new Date(), minute);
    }

    public static Date addMinutes(Date sourceDate, int minute) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(12, minute);
        return calendar.getTime();
    }

    public static Date addHours(int hour) {
        return addHours(new Date(), hour);
    }

    public static Date addHours(Date sourceDate, int hour) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(10, hour);
        return calendar.getTime();
    }

    public static Date addWeeks(int week) {
        return addWeeks(new Date(), week);
    }

    public static Date addWeeks(Date sourceDate, int week) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(3, week);
        return calendar.getTime();
    }

    public static Date addMonths(int month) {
        return addMonths(new Date(), month);
    }

    public static Date addMonths(Date sourceDate, int month) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(2, month);
        return calendar.getTime();
    }

    public static Date addYears(int year) {
        return addYears(new Date(), year);
    }

    public static Date addYears(Date sourceDate, int year) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(1, year);
        return calendar.getTime();
    }

    public static Date subMillisecond(long millisecond) {
        return addMillisecond(new Date(), -millisecond);
    }

    public static Date subMillisecond(Date sourceDate, long millisecond) {
        return addMillisecond(sourceDate, -millisecond);
    }

    public static Date subDays(int day) {
        return addDays(new Date(), -day);
    }

    public static Date subDays(Date sourceDate, int day) {
        return addDays(sourceDate, -day);
    }

    public static Date subSeconds(int second) {
        return addSeconds(new Date(), -second);
    }

    public static Date subSeconds(Date sourceDate, int second) {
        return addSeconds(sourceDate, -second);
    }

    public static Date subMinutes(int minute) {
        return addMinutes(new Date(), -minute);
    }

    public static Date subMinutes(Date sourceDate, int minute) {
        return addMinutes(sourceDate, -minute);
    }

    public static Date subHours(int hour) {
        return addHours(new Date(), -hour);
    }

    public static Date subHours(Date sourceDate, int hour) {
        return addHours(sourceDate, -hour);
    }

    public static Date subWeeks(int week) {
        return addWeeks(new Date(), -week);
    }

    public static Date subWeeks(Date sourceDate, int week) {
        return addWeeks(sourceDate, -week);
    }

    public static Date subMonths(int month) {
        return addMonths(new Date(), -month);
    }

    public static Date subMonths(Date sourceDate, int month) {
        return addMonths(sourceDate, -month);
    }

    public static Date subYears(int year) {
        return addYears(new Date(), -year);
    }

    public static Date subYears(Date sourceDate, int year) {
        return addYears(sourceDate, -year);
    }

    public static String format(Date sourceDate, String format) {
        if (CommUtil.null2String(format).equals("")) {
            throw new IllegalArgumentException("format参数不允许为空，请检查重试！");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            String returnValue = dateFormat.format(sourceDate);
            return returnValue;
        }
    }

    public static String formatByDate(Date sourceDate) {
        return format(sourceDate, "yyyy-MM-dd");
    }

    public static String formatByDate(long millisecond) {
        return formatByDate(millisecond, "yyyy-MM-dd");
    }

    public static String formatByDate(long millisecond, String format) {
        if (millisecond < 0L) {
            throw new IllegalArgumentException("入参millisecond不允许小于0！");
        } else {
            Date sourceDate = new Date(millisecond);
            String returnValue = format(sourceDate, format);
            return returnValue;
        }
    }

    public static String formatByDate(String sourceStr) {
        return formatByDate(CommUtil.null2Long(sourceStr));
    }

    public static String formatByDate(String sourceStr, String formatter) {
        return formatByDate(CommUtil.null2Long(sourceStr), formatter);
    }

    public static String formatByDateTime(long millisecond) {
        return format(new Date(millisecond), "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatByDateTime(Date sourceDate) {
        return format(sourceDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatByTime(Date sourceDate) {
        return format(sourceDate, "HH:mm:ss");
    }

    public static Date parse(String sourceStr, String parse) {
        Date returnValue = null;
        if (CommUtil.null2String(sourceStr).equals("")) {
            throw new IllegalArgumentException("sourceStr参数不允许为空，请检查重试！");
        } else if (CommUtil.null2String(parse).equals("")) {
            throw new IllegalArgumentException("parse参数不允许为空，请检查重试！");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(parse);

            try {
                returnValue = dateFormat.parse(sourceStr);
            } catch (ParseException var5) {
                log.error(String.format("把指定时间转换为日期对象时出错：%s", var5));
            }

            return returnValue;
        }
    }

    public static Date parseByDateTime(String sourceStr) {
        return parse(sourceStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseByDateTime(long millisecond) {
        if (millisecond < 0L) {
            throw new IllegalArgumentException("入参millisecond不允许小于0！");
        } else {
            return new Date(millisecond);
        }
    }

    public static Date parseByDate(String sourceStr) {
        return parse(sourceStr, "yyyy-MM-dd");
    }

    public static Date parseByTime(String sourceStr) {
        return parse(sourceStr, "HH:mm:ss");
    }

    public static Date getMonthFirstDay() {
        return getMonthFirstDay(new Date());
    }

    public static Date getMonthFirstDay(Date sourceDate) {
        Calendar calendar = getCalendar(sourceDate);
        int actualMinimum = calendar.getActualMinimum(5);
        calendar.set(5, actualMinimum);
        return calendar.getTime();
    }

    public static Date getMonthEndDay() {
        return getMonthEndDay(new Date());
    }

    public static Date getMonthEndDay(Date sourceDate) {
        Calendar calendar = getCalendar(sourceDate);
        int actualMaximum = calendar.getActualMaximum(5);
        calendar.set(5, actualMaximum);
        return calendar.getTime();
    }

    public static Date getWeekFirstDay() {
        return getWeekFirstDay(new Date());
    }

    public static Date getWeekFirstDay(Date sourceDate) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.setFirstDayOfWeek(2);
        calendar.set(7, 2);
        return calendar.getTime();
    }

    public static Date getWeekEndDay() {
        return getWeekEndDay(new Date());
    }

    public static Date getWeekEndDay(Date sourceDate) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.setFirstDayOfWeek(2);
        calendar.set(7, 1);
        return calendar.getTime();
    }

    public static int getDayOfWeek(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            Calendar calendar = getCalendar(sourceDate);
            int week = calendar.get(7);
            int returnValue;
            if (week == 1) {
                returnValue = week + 6;
            } else {
                returnValue = week - 1;
            }

            return returnValue;
        }
    }

    public static int getDayOfWeek(String sourceStr) {
        return getDayOfWeek(sourceStr, "yyyy-MM-dd");
    }

    public static int getDayOfWeek(String sourceStr, String formatter) {
        if (CommUtil.null2String(sourceStr).equals("")) {
            throw new IllegalArgumentException("入参sourceStr不允许为空！");
        } else {
            formatter = CommUtil.null2String(formatter).equals("") ? "yyyy-MM-dd" : CommUtil.null2String(formatter);
            Date sourceDate = parse(sourceStr, formatter);
            return getDayOfWeek(sourceDate);
        }
    }

    public static Date getPreviousMonthEndDay() {
        return getPreviousMonthEndDay(new Date());
    }

    public static Date getPreviousMonthEndDay(Date sourceDate) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.set(5, 1);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static Date getNextMonthFirstDay() {
        return getNextMonthFirstDay(new Date());
    }

    public static Date getNextMonthFirstDay(Date sourceDate) {
        Calendar calendar = getCalendar(sourceDate);
        calendar.add(2, 1);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static Date getNextWeekDay() {
        return getNextWeekDay(new Date());
    }

    public static Date getNextWeekDay(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            return addWeeks(sourceDate, 1);
        }
    }

    public static Date keepDateClearTime() {
        return keepDateClearTime(new Date());
    }

    public static Date keepDateClearTime(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            Calendar calendar = getCalendar(sourceDate);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            return calendar.getTime();
        }
    }

    public static int getMinute(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            return getCalendar(sourceDate).get(12);
        }
    }

    public static int getMonth(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            return getCalendar(sourceDate).get(2) + 1;
        }
    }

    public static int getMonth(String sourceStr) {
        return getMonth(sourceStr, "yyyy-MM-dd");
    }

    public static int getMonth(String sourceStr, String formatter) {
        if (CommUtil.null2String(sourceStr).equals("")) {
            throw new IllegalArgumentException("入参sourceStr不允许为空！");
        } else if (CommUtil.null2String(formatter).equals("")) {
            throw new IllegalArgumentException("入参formatter不允许为空！");
        } else {
            Date sourceDate = parse(sourceStr, formatter);
            int returnValue = getMonth(sourceDate);
            return returnValue;
        }
    }

    public static long getSecond(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            return (long) getCalendar(sourceDate).get(13);
        }
    }

    public static int getHour(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            return getCalendar(sourceDate).get(11);
        }
    }

    public static int getYear(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            return getCalendar(sourceDate).get(1);
        }
    }

    public static int getYear(String sourceStr) {
        return getYear(sourceStr, "yyyy-MM-dd");
    }

    public static int getYear(String sourceStr, String formatter) {
        if (CommUtil.null2String(sourceStr).equals("")) {
            throw new IllegalArgumentException("入参sourceStr不允许为空！");
        } else if (CommUtil.null2String(formatter).equals("")) {
            throw new IllegalArgumentException("入参formatter不允许为空！");
        } else {
            Date sourceDate = parse(sourceStr, formatter);
            int returnValue = getYear(sourceDate);
            return returnValue;
        }
    }

    public static long getMillis(Date sourceDate) {
        return sourceDate.getTime();
    }

    public static int getDayOfMonth() {
        return getDayOfMonth(new Date());
    }

    public static int getDayOfMonth(Date sourceDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else {
            return getCalendar(sourceDate).get(5);
        }
    }

    public static long diffDate(Date startDate, Date endDate) {
        Calendar startCalendar = getCalendar(startDate);
        Calendar endCalendar = getCalendar(endDate);
        int startDay = startCalendar.get(6);
        int endDay = endCalendar.get(6);
        int startYear = startCalendar.get(1);
        int endYear = endCalendar.get(1);
        if (startYear == endYear) {
            return (long) (endDay - startDay);
        } else {
            int returnValue = 0;

            for (int i = startYear; i < endYear; ++i) {
                if (isLeapYear(i)) {
                    returnValue += 366;
                } else {
                    returnValue += 365;
                }
            }

            return (long) (returnValue + (endDay - startDay));
        }
    }

    public static long diffMilli(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("入参startDate不允许为空！");
        } else if (endDate == null) {
            throw new IllegalArgumentException("入参endDate不允许为空！");
        } else {
            return endDate.getTime() - startDate.getTime();
        }
    }

    public static long diffSecond(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("入参startDate不允许为空！");
        } else if (endDate == null) {
            throw new IllegalArgumentException("入参endDate不允许为空！");
        } else {
            return diffMilli(startDate, endDate) / 1000L;
        }
    }

    public static long diffMinute(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("入参startDate不允许为空！");
        } else if (endDate == null) {
            throw new IllegalArgumentException("入参endDate不允许为空！");
        } else {
            return diffSecond(startDate, endDate) / 60L;
        }
    }

    public static long diffHour(Date startDate, Date endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("入参startDate不允许为空！");
        } else if (endDate == null) {
            throw new IllegalArgumentException("入参endDate不允许为空！");
        } else {
            return diffMinute(startDate, endDate) / 60L;
        }
    }

    public static boolean isSameDay(Date sourceDate, Date targetDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else if (targetDate == null) {
            throw new IllegalArgumentException("入参targetDate不允许为空！");
        } else {
            Calendar start = getCalendar(sourceDate);
            Calendar end = getCalendar(targetDate);
            boolean returnValue = start.get(1) == end.get(1) && start.get(2) == end.get(2) && start.get(5) == end.get(5);
            return returnValue;
        }
    }

    public static boolean isSameMonth(Date sourceDate, Date targetDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else if (targetDate == null) {
            throw new IllegalArgumentException("入参targetDate不允许为空！");
        } else {
            Calendar start = getCalendar(sourceDate);
            Calendar end = getCalendar(targetDate);
            boolean returnValue = start.get(1) == end.get(1) && start.get(2) == end.get(2);
            return returnValue;
        }
    }

    public static boolean isSameYear(Date sourceDate, Date targetDate) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("入参sourceDate不允许为空！");
        } else if (targetDate == null) {
            throw new IllegalArgumentException("入参targetDate不允许为空！");
        } else {
            Calendar start = getCalendar(sourceDate);
            Calendar end = getCalendar(targetDate);
            boolean returnValue = start.get(1) == end.get(1);
            return returnValue;
        }
    }

    public static boolean isLeapYear(int year) {
        if (year < 1852) {
            throw new IllegalArgumentException(String.format("入参year必须大于或等于1582！"));
        } else {
            boolean returnValue = year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
            return returnValue;
        }
    }

    public static boolean isLeapYear(Date sourceDate) {
        Calendar calendar = getCalendar(sourceDate);
        int year = calendar.get(1);
        boolean returnValue = year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
        return returnValue;
    }

    public static Date getBirthDayFromIDCard(String idNumber) {
        Calendar calendar = getCalendar();
        int length = idNumber.length();
        switch (length) {
            case 15:
                calendar.set(1, Integer.valueOf("19" + idNumber.substring(6, 8)));
                calendar.set(2, Integer.valueOf(idNumber.substring(8, 10)) - 1);
                calendar.set(5, Integer.valueOf(idNumber.substring(10, 12)));
                break;
            case 18:
                calendar.set(1, Integer.valueOf(idNumber.substring(6, 10)));
                calendar.set(2, Integer.valueOf(idNumber.substring(10, 12)) - 1);
                calendar.set(5, Integer.valueOf(idNumber.substring(12, 14)));
                break;
            default:
                throw new IllegalArgumentException("入参idNumber长度不正确，非15位或18位，请检查重试！");
        }

        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTime();
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static Calendar getCalendar(Date sourceDate) {
        Calendar calendar = getCalendar();
        if (sourceDate == null) {
            throw new RuntimeException(String.format("入参sourceDate不允许为空！"));
        } else {
            calendar.setTime(sourceDate);
            return calendar;
        }
    }
}
