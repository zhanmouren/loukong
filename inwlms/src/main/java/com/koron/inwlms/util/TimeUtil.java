package com.koron.inwlms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author csh
 * 时间工具类
 **/
public class TimeUtil {

    /**
     * 获取文本格式时间
     *
     * @param date date
     * @return 文本时间
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * date 转localDateTime
     *
     * @param date date
     * @return localDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * date 转localDate
     *
     * @param date date
     * @return localDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * localDate转date
     *
     * @param localDate localDate
     * @return date
     */
    public static Date localDateToDate(LocalDate localDate) {
        return localDateTimeToDate(localDate.atStartOfDay());
    }

    /**
     * LocalDate转化为指定格式字符串
     *
     * @param localDate localDate
     * @return .
     */
    public static String localDateToString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String localTimeToString(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static LocalDate stringToLocalDate(String time) {
        return LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDateTime stringToLocalDateTime(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * localDateTime转date
     *
     * @param dateTime local
     * @return date
     */
    public static Date localDateTimeToDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得时间戳
     *
     * @param localDateTime lo
     * @return .
     */
    public static long localDateTimeToStamp(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 查询一个日期(年月日)到目前过了多少年
     *
     * @param startTime 开始时间
     * @return 相差数量
     */
    public static Integer getYearsByStartTime(String startTime) {
        LocalDate startDate = stringToLocalDate(startTime);
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isBefore(startDate)) {
            return 0;
        } else {
            return startDate.until(currentDate).getYears();
        }
    }

    /**
     * Long类型时间戳转化为LocalDateTime
     *
     * @param stamp 时间戳
     * @return .
     */
    public static LocalDateTime stampToLocalDateTime(Long stamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(stamp), ZoneId.systemDefault());
    }


    /**
     * 自定义格式获取文本时间
     *
     * @param date   date
     * @param format 格式("yyyy-MM-dd")
     * @return string 文本
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        return format1.format(date);
    }

    /**
     * 获取时间对象
     *
     * @param date 文本格式时间
     * @return 时间对象
     * @throws ParseException format exception
     */
    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(date);
    }


    /**
     * 自定义格式获取时间对象
     *
     * @param date   文本时间
     * @param format format 转化格式
     * @return 时间对象
     * @throws ParseException format exceptions
     */
    public static Date stringToDate(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    /**
     * 文本时间转化为日期
     *
     * @param str 文本时间
     * @return 日期
     * @throws ParseException format EX
     */
    public static Calendar stringDateToCalendar(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(str));
        return calendar;
    }

    /**
     * 日期转时间
     *
     * @param calendar 日期
     * @param format   格式
     * @return 时间
     */
    public static Date calendarToDate(Calendar calendar, String format) throws ParseException {
        return stringToDate(calendarToStringDate(calendar, format));
    }

    /**
     * 日期转文本时间
     *
     * @param calendar 日期
     * @param format   格式
     * @return 文本时间
     */
    public static String calendarToStringDate(Calendar calendar, String format) {
        return dateToString(calendar.getTime(), format);
    }

    /**
     * 获取日期时间当月的总天数，如2017-02-13，返回28
     *
     * @param date 时间对象
     * @return days
     */
    public static int getAllDaysOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取日期时间的天数，如2017-02-13，返回13
     *
     * @param date 时间对象
     * @return 如2017-02-13，返回13
     */
    public static int getDays(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取日期时间的年份，如2017-02-13，返回2017
     *
     * @param date 时间对象
     * @return years
     */
    public static int getYears(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期时间的月份，如2017年2月13日，返回2
     *
     * @param date 时间对象
     * @return month
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return date
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return date
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 将文本时间转换为时间戳
     *
     * @param stringDate 文本时间
     * @return 时间戳
     */
    public static Long stringDateToStamp(String stringDate) throws ParseException {
        return TimeUtil.stringToDate(stringDate).getTime();
    }

    /**
     * 将时间戳转换为文本时间
     *
     * @param timeStamp 时间戳
     * @return 文本时间
     */
    public static String stampToStringDate(Long timeStamp) {
        long l = timeStamp;
        return TimeUtil.dateToString(new Date(l));
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，不包含今天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     * @throws ParseException 转换异常
     */
    public static int dateBetween(String startDate, String endDate) throws ParseException {
        Date dateStart = stringToDate(startDate, "yyyy-MM-dd");
        Date dateEnd = stringToDate(endDate, "yyyy-MM-dd");
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);
    }

    /**
     * 获取两个日期（不含时分秒）相差的天数，包含今天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差的天数
     * @throws ParseException 转换异常
     */
    public static int dateBetweenIncludeToday(String startDate, String endDate) throws ParseException {
        return dateBetween(startDate, endDate) + 1;
    }
    
    /**
	 * 获取某一年的最后一天
	 * @param year
	 * @return
	 */
	 public static String getLastDayOfMonth(int year) {     
         Calendar cal = Calendar.getInstance();     
         cal.set(Calendar.YEAR, year);     
         cal.set(Calendar.MONTH, 11);     
         cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
        return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());  
     } 
	 
	 /**
	  * 获取某一年的第一天
	  * @param year
	  * @return
	  */
     public static String getFirstDayOfMonth(int year) {     
         Calendar cal = Calendar.getInstance();     
         cal.set(Calendar.YEAR, year);     
         cal.set(Calendar.MONTH, 0);  
         cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
        return   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());  
     }
     
     /**
      * 获取某个月份的第一天的时间
      * @param num 与当前月的差值，例如：上个月,num = -1
      * @return
      */
     public static String getMonthFirstDay(int num) {
    	 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	 Calendar calendar=Calendar.getInstance();
    	 calendar.add(Calendar.MONTH, num);
    	 calendar.set(Calendar.DAY_OF_MONTH, 1);
    	 return format.format(calendar.getTime());
     }
     
     /**
      * 获取当前时间
      * @param num 与当前月的差值，例如：上个月,num = -1
      * @return
      */
     public static String getcurrentDay(String formatStr,Date date) {
    	 SimpleDateFormat format=new SimpleDateFormat(formatStr);
    	 return format.format(date);
     }
     
     /**
      * 获取某月的最后一天
      *
      */
     public static String getLastDayOfMonth(int year,int month)
     {
         Calendar cal = Calendar.getInstance();
         //设置年份
         cal.set(Calendar.YEAR,year);
         //设置月份
         cal.set(Calendar.MONTH, month-1);
         //获取某月最大天数
         int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
         //设置日历中月份的最大天数
         cal.set(Calendar.DAY_OF_MONTH, lastDay);
         //格式化日期
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         String lastDayOfMonth = sdf.format(cal.getTime());
           
         return lastDayOfMonth;
     }
}
