package com.oscar.springbootstudy.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @description: LocalDate, LocalDateTime日期工具类
 * @author: Oscar
 * @create: 2019-05-01 22:31
 **/
public class LocalDateUtils {

    public static final String DEFAULT_MONTH_PATTERN = "yyyy-MM";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_TIME_SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DATE_START_TIME = " 00:00:00";
    public static final String DATE_END_TIME = " 23:59:59";

    public static final ZoneId EST_ZONE_ID = ZoneId.of("America/New_York");
    public static final ZoneId CTT_ZONE_ID = ZoneId.of("Asia/Shanghai");
    public static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    /*
     * 运算类型-加法
     */
    public static final String OPERATION_TYPE_PLUS = "PLUS";
    /*
     * 运算类型-减法
     */
    public static final String OPERATION_TYPE_MINUS = "MINUS";

    /*
     * 时间类型-年
     */
    public static final String TIME_TYPE_YEARS = "YEARS";
    /*
     * 时间类型-月
     */
    public static final String TIME_TYPE_MONTHS = "MONTHS";
    /*
     * 时间类型-周
     */
    public static final String TIME_TYPE_WEEKS = "WEEKS";
    /*
     * 时间类型-年日
     */
    public static final String TIME_TYPE_DAYS = "DAYS";

    /*
    * 快选时间控制
    */
    public static final String DATE_SCOPE_TODAY = "today";
    public static final String DATE_SCOPE_YESTERDAY = "yesterday";
    public static final String DATE_SCOPE_THISWEEK = "thisWeek";
    public static final String DATE_SCOPE_LASTWEEK = "lastWeek";
    public static final String DATE_SCOPE_THISMONTH = "thisMonth";
    public static final String DATE_SCOPE_LASTMONTH = "lastMonth";
    public static final String DATE_SCOPE_SIXTY_DAYS = "sixtyDays";
    public static final String DATE_SCOPE_LAST_SEVEN_DAYS = "lastSevenDays";
    public static final Integer DATE_SCOPE_SIXTY_DAYS_NUM = 60;
    public static final Integer DATE_SCOPE_LAST_SEVEN_DAYS_NUM = 7;

    /**
     * 格式化为 yyyy-MM-dd HH:mm:ss
     * @param localDateTime
     * @return
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    }

    /**
     * 格式化为 yyyy-MM-dd
     * @param localDateTime
     * @return
     */
    public static String formatDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));
    }

    /**
     * 获取传入格式的字符串日期(今日)
     *
     * @param pattern 格式
     * @return
     */
    public static String getToday(String pattern){
        return getEstDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @Description 获取北京时间今天的字符串日期
     * @Date 2019/7/17 21:02
     * @Param [pattern]
     * @return java.lang.String
     */
    public static String getCttToday(String pattern){
        return getCttDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取传入格式的字符串日期（过去某日）
     *
     * @param minus   减去的天数
     * @param pattern 格式
     * @return
     */
    public static String getPastDate(Integer minus, String pattern){
        return getEstDateTime()
                .minusDays(minus)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取传入格式的字符串日期（过去某日） 北京时间
     *
     * @param minus   减去的天数
     * @param pattern 格式
     * @return
     */
    public static String getCttPastDate(Integer minus, String pattern){
        return getCttDateTime()
                .minusDays(minus)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取传入格式的字符串日期（昨日）
     *
     * @param pattern 格式
     * @return
     */
    public static String getYesterday(String pattern){
        return getPastDate(1, pattern);
    }

    /**
     * @Description 获取传入格式的字符串日期（前日）
     * @Date 2019/8/1 1:03
     * @Param [pattern]
     * @return java.lang.String
     */
    public static String getBeforeYesterday(String pattern){
        return getPastDate(2, pattern);
    }

    /**
     * 获取传入格式的字符串日期（昨日）北京时间
     *
     * @param pattern 格式
     * @return
     */
    public static String getCttYesterday(String pattern){
        return getPastDate(1, pattern);
    }

    /**
     * 获取指定格式前几个月的第一天
     *
     * @param minus 减去的月份数
     * @param pattern 格式
     * @return
     */
    public static String getPastMonthFirstDate(Integer minus, String pattern){
        return getEstDateTime()
                .minusMonths(minus)
                .with(TemporalAdjusters.firstDayOfMonth())
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取指定格式前几个月的最后一天
     *
     * @param minus 减去的月份数
     * @param pattern 格式
     * @return
     */
    public static String getPastMonthLastDate(Integer minus, String pattern){
        return getEstDateTime()
                .minusMonths(minus)
                .with(TemporalAdjusters.lastDayOfMonth())
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取上月第一天
     *
     * @return
     */
    public static String getLastMonthFirstDate(String pattern){
        return getPastMonthFirstDate(1, pattern);
    }

    /**
     * 判断是否是最近X个自然月：2-最近两个月，3-最近三个月，等等
     * 包含本月，如当前是5月，最近两个月是4，5月
     *
     * @param x 最近X个月
     * @param date String型时间
     * @return
     */
    public static boolean isLastXMonth(Integer x, String date) {
        LocalDate start = LocalDate.parse(date, DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN))
                .with(TemporalAdjusters.firstDayOfMonth());
        LocalDate now = getEstDate().with(TemporalAdjusters.firstDayOfMonth());
        return !start.isBefore(now.minusMonths(x - 1));
    }

    /**
     * 结束日期是否超过开始日期X个月（是否跨X个月）
     *
     * @param x 月份数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public static boolean isOverXMonth(Integer x, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN))
                .with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN))
                .with(TemporalAdjusters.firstDayOfMonth());
        return start.isEqual(end.minusMonths(x));
    }

    /**
     * 结束日期是否超过开始日期X个月（是否跨X个月）
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public static boolean isSameMonth(String startDate, String endDate) {
        return isOverXMonth(0, startDate, endDate);
    }

    /**
     * 获取当前月份减去X月的数据
     * @param x 月份数
     * @return
     */
    public static List<Map<String,Object>> getMinusMonths(Integer x){
        // 当前日期
        LocalDate localDate = LocalDate.now();
        // 日期的列表
        List<Map<String,Object>> monthsList = new ArrayList<>();
        for(int i = 0 ; i < x ; i ++){
            Map<String,Object> map = new HashMap<>();
            // 需求要求显示当前月和上月，其他按月份显示
            if(i == 0){
                map.put("monthsStr", "本月");
            } else if(i == 1){
                map.put("monthsStr", "上月");
            } else {
                map.put("monthsStr", localDate.minusMonths(i).getMonthValue() + "月");
            }
            // 第一天
            map.put("firstMonthsDay",getPastMonthFirstDate(i,DEFAULT_DATE_PATTERN) + " 00:00:00");
            // 最后一天
            map.put("lastMonthsDay",getPastMonthLastDate(i,DEFAULT_DATE_PATTERN) + " 23:59:59");
            monthsList.add(map);
        }
        return monthsList;
    }

    /**
     * 当前美东时间，返回LocalDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime getEstDateTime() {
        return LocalDateTime.now(EST_ZONE_ID);
    }

    /**
     * 当前北京时间，返回LocalDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime getCttDateTime() {
        return LocalDateTime.now(CTT_ZONE_ID);
    }

    /**
     * 当前格林时间，返回LocalDateTime
     * @return LocalDateTime
     */
    public static LocalDateTime getUtcDateTime() {
        return LocalDateTime.now(UTC_ZONE_ID);
    }

    /**
     * 当前美东时间，返回LocalDate
     * @return LocalDate
     */
    public static LocalDate getEstDate() {
        return LocalDate.now(EST_ZONE_ID);
    }

    /**
     * 当前北京时间，返回LocalDate
     * @return LocalDate
     */
    public static LocalDate getCttDate() {
        return LocalDate.now(CTT_ZONE_ID);
    }

    /**
     * 当前格林时间，返回LocalDate
     * @return LocalDate
     */
    public static LocalDate getUtcDate() {
        return LocalDate.now(UTC_ZONE_ID);
    }

    /**
     * localDateTime类型转为Date类型
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    /**
     * localDateTime类型转为Date类型
     * @param localDateTime
     * @return
     */
    public static Timestamp localDateTime2Timestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    /**
     * 当前美东时间，返回Date
     * @return Date
     */
    public static Date getEstOldDate() {
        return localDateTime2Date(getEstDateTime());
    }

    /**
     * 当前北京时间，返回Date
     * @return Date
     */
    public static Date getCttOldDate() {
        return localDateTime2Date(getCttDateTime());
    }

    /**
     * Date类型转为LocalDateTime类型
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Date类型转为LocalDateTime类型
     * @param date 时间
     * @param zone 时区
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date, ZoneId zone) {
        return LocalDateTime.ofInstant(date.toInstant(), zone);
    }

    /**
     * 当前美东时间点的时间戳
     * @return Date
     */
    public static Long getEstEpochMilli() {
        return getEpochMilli(getEstDateTime(), EST_ZONE_ID);
    }

    /**
     * 当前北京时间点的时间戳
     * @return Date
     */
    public static Long getCttEpochMilli() {
        return getEpochMilli(getCttDateTime(), CTT_ZONE_ID);
    }

    /**
     * 获取时间戳
     * @param localDateTime 日期时间
     * @param zoneId 时区
     * @return
     */
    public static Long getEpochMilli(LocalDateTime localDateTime, ZoneId zoneId) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zoneId, "zoneId");
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * 根据时间戳获取LocalDateTime
     * @param timestamp 时间戳
     * @param zoneId 时区
     * @return
     */
    public static LocalDateTime toLocalDateTime(Long timestamp, ZoneId zoneId) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 在给定的时间上加上999毫秒。如：2019-06-07 12:34:21 转换为 2019-06-07 12:34:21.999
     * @param localDateTime
     * @return
     */
    public static LocalDateTime plus999MilliSeconds(LocalDateTime localDateTime) {
        return localDateTime.plusNanos(999000000);
    }

    /**
     * 在给定的时间上加上999毫秒。如：2019-06-07 12:34:21 转换为 2019-06-07 12:34:21.999
     * @param localDateTimeStr
     * @return
     */
    public static String plus999MilliSeconds(String localDateTimeStr) {
        if(StringUtils.isBlank(localDateTimeStr)){
            return localDateTimeStr;
        }
        //String->LocalDateTime
        LocalDateTime localDateTime = string2LocalDateTime(localDateTimeStr);
        if(null == localDateTime){
            return localDateTimeStr;
        }
        //加上999毫秒
        localDateTime = plus999MilliSeconds(localDateTime);
        //LocalDateTime->String
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_SECOND_PATTERN);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * LocalDate类型转换为LocalDateTime
     * @param localDate
     * @return
     */
    public static LocalDateTime localDate2LocalDateTime(LocalDate localDate) {
        return localDate.atStartOfDay();
    }

    /**
     * 给定日期获取当天最后1毫秒的时间。如：2019-06-07 转换为 2019-06-07 23:59:59.999
     * @param localDate
     * @return
     */
    public static LocalDateTime lastMilliSecondOfday(LocalDate localDate) {
        return localDate.atStartOfDay().plusDays(1).minusNanos(1000000);
    }

    /**
     * 时区相互转换
     * @param localDateTime 日期时间
     * @param src 源时区
     * @param des 目标时区
     * @return
     */
    public static LocalDateTime toOtherZone(LocalDateTime localDateTime, ZoneId src, ZoneId des) {
        long epochMilli = getEpochMilli(localDateTime, src);
        return toLocalDateTime(epochMilli, des);
    }

    /**
     * 美东时区转为北京时区
     * @param localDateTime 日期时间
     * @return
     */
    public static LocalDateTime estToCtt(LocalDateTime localDateTime) {
        return toOtherZone(localDateTime, EST_ZONE_ID, CTT_ZONE_ID);
    }

    /**
     * 美东时区转为北京时区
     *
     * @param localDateTimeStr 日期时间字符串
     * @return
     */
    public static String estToCtt(String localDateTimeStr) {
        if (StringUtils.isBlank(localDateTimeStr)) {
            return null;
        }
        //String->LocalDateTime->转时区->String
        LocalDateTime localDateTime = string2LocalDateTime(localDateTimeStr);
        localDateTime = estToCtt(localDateTime);
        return localDateTime2String(localDateTime);
    }

    /**
     * 美东时区转为北京时区
     *
     * @param date 日期时间
     * @return
     */
    public static String estToCtt(Date date) {
        if (null == date) {
            return null;
        }
        //Date->LocalDateTime->转时区->String
        LocalDateTime localDateTime = date2LocalDateTime(date);
        localDateTime = estToCtt(localDateTime);
        return localDateTime2String(localDateTime);
    }

    /**
     * 北京时区转为美东时区
     * @param localDateTime 日期时间
     * @return
     */
    public static LocalDateTime cttToEst(LocalDateTime localDateTime) {
        return toOtherZone(localDateTime, CTT_ZONE_ID, EST_ZONE_ID);
    }

    /**
     * 北京时区转为美东时区
     *
     * @param localDateTimeStr 日期时间字符串
     * @return
     */
    public static String cttToEst(String localDateTimeStr) {
        if (StringUtils.isBlank(localDateTimeStr)) {
            return null;
        }
        //String->LocalDateTime->转时区->String
        LocalDateTime localDateTime = string2LocalDateTime(localDateTimeStr);
        localDateTime = cttToEst(localDateTime);
        return localDateTime2String(localDateTime);
    }

    /**
     * 北京时区转为美东时区
     *
     * @param date 日期时间
     * @return
     */
    public static String cttToEst(Date date) {
        if (null == date) {
            return null;
        }
        //Date->LocalDateTime->转时区->String
        LocalDateTime localDateTime = date2LocalDateTime(date);
        localDateTime = cttToEst(localDateTime);
        return localDateTime2String(localDateTime);
    }

    /**
     * 北京时区转为美东时区
     *
     * @param date 日期时间
     * @return
     */
    public static Date cttToEstDate(Date date) {
        if (null == date) {
            return null;
        }
        //Date->LocalDateTime->转时区->String
        LocalDateTime localDateTime = date2LocalDateTime(date);
        localDateTime = cttToEst(localDateTime);
        return localDateTime2Date(localDateTime);
    }

    /**
     * @param : [localDateTime]
     * @return : java.time.LocalDateTime
     * @description : 字符串转换成LocalDateTime日期
     * @author : Masker
     * @date : 1:17 2019/7/17
     */
    public static LocalDateTime string2LocalDateTime(String localDateTimeStr) {
        if (StringUtils.isBlank(localDateTimeStr)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);
        return LocalDateTime.parse(localDateTimeStr, dateTimeFormatter);
    }

    /**
     * @param : [localDate]
     * @return : java.time.LocalDate
     * @description : 字符串转换成LocalDate日期
     * @author : Masker
     * @date : 1:17 2019/7/17
     */
    public static LocalDate string2LocalDate(String localDateStr) {
        if (StringUtils.isBlank(localDateStr)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
        return LocalDate.parse(localDateStr, dateTimeFormatter);
    }

    /**
     * @param : [localDateTime]
     * @return : java.lang.String
     * @description :  LocalDateTime日期转换成字符串
     * @author : Masker
     * @date : 1:17 2019/7/17
     */
    public static String localDateTime2String(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN);
        return dateTimeFormatter.format(localDateTime);
    }

    /**
     * @param : [localDate]
     * @return : java.lang.String
     * @description :  LocalDate日期转换成字符串
     * @author : Masker
     * @date : 1:17 2019/7/17
     */
    public static String localDate2String(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
        return dateTimeFormatter.format(localDate);
    }

    /**
     * 本周第一天的日期
     * @return
     */
    public static LocalDate firstDayOfCurrentWeek(LocalDate localDate) {
        return localDate.with(DayOfWeek.MONDAY);
    }

    /**
     * 上周第一天的日期
     * @return
     */
    public static LocalDate firstDayOfLastWeek(LocalDate localDate) {
        return localDate.minusWeeks(1).with(DayOfWeek.MONDAY);
    }

    /**
     * 上周最后一天的日期
     * @return
     */
    public static LocalDate lastDayOfLastWeek(LocalDate localDate) {
        return localDate.minusWeeks(1).with(DayOfWeek.SUNDAY);
    }

    /**
     * 本月第一天的日期
     * @return
     */
    public static LocalDate firstDayOfCurrentMonth(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 上月第一天的日期
     * @return
     */
    public static LocalDate firstDayOfLastMonth(LocalDate localDate) {
        return localDate.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 上月最后一天的日期
     * @return
     */
    public static LocalDate lastDayOfLastMonth(LocalDate localDate) {
        return localDate.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 日期运算操作
     * @param localDate 传入日期
     * @param operationType 运算类型
     * @param timeType 时间类型（YEARS，MONTHIS, WEEKS, DAYS）
     * @param num 运算值
     * @return
     */
    public static LocalDate operationLocalDate(LocalDate localDate, String operationType, String timeType, Integer num) {
        if(OPERATION_TYPE_PLUS.equals(operationType)){
            //加法
            switch (timeType){
                case TIME_TYPE_YEARS:
                    return localDate.plusYears(num);
                case TIME_TYPE_MONTHS:
                    return localDate.plusMonths(num);
                case TIME_TYPE_WEEKS:
                    return localDate.plusWeeks(num);
                case TIME_TYPE_DAYS:
                    return localDate.plusDays(num);
                default:
                    return localDate;
            }
        } else if(OPERATION_TYPE_MINUS.equals(operationType)){
            //减法
            switch (timeType){
                case TIME_TYPE_YEARS:
                    return localDate.minusYears(num);
                case TIME_TYPE_MONTHS:
                    return localDate.minusMonths(num);
                case TIME_TYPE_WEEKS:
                    return localDate.minusWeeks(num);
                case TIME_TYPE_DAYS:
                    return localDate.minusDays(num);
                default:
                    return localDate;
            }
        }
        return localDate;
    }

    public static Integer daysBetweenTwoDates(LocalDate start, LocalDate end) {
        Period next = Period.between(start, end);
        return next.getDays();
//        return end.toEpochDay() - start.toEpochDay();
    }



    public static void main(String[] args) {
        System.out.println(cttToEst("2019-07-07 12:23:34"));
//        System.out.println(getLastMonthFirstDate(DEFAULT_DATE_PATTERN));N
//        System.out.println(getPastMonthFirstDate(2, DEFAULT_DATE_PATTERN));
//        System.out.println(isLastXMonth(3, "2019-02-01"));
//        System.out.println(isOverXMonth(1, "2019-01-01", "2019-02-02"));
//        System.out.println(isSameMonth("2018-02-11", "2018-02-02"));


//        Date now = LocalDateUtils.getEstDate();  // 替换 new Date();
//        // 当前日期时间尽量使用 LocalDateTime，而不是使用Date类，现在mybatis和postgresql driver已经支持JDK8的日期时间类
//        LocalDateTime currentTime = LocalDateUtils.getEstDateTime();
//        Long start = LocalDateUtils.getEstEpochMilli(); // 替换 System.currentTimeMillis();

//        System.out.println(System.currentTimeMillis());
//        System.out.println(getCttEpochMilli());
//        System.out.println(getEstEpochMilli());

//        LocalDateTime a = LocalDateTime.of(2017, 7, 16, 7, 24, 21);
//        System.out.println(startTime(a));
//        System.out.println(endTime(a).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

//        LocalDate date = LocalDate.of(2017, 7, 16);
//        System.out.println(startTime(date));
//
//        LocalDate date1 = LocalDate.of(2017, 7, 16);
//        System.out.println(endTime(date1));

//        LocalDateTime dateTime = getEstDateTime();
//        Date date = localDateTime2Date(dateTime);
//        System.out.println(dateTime);
//        System.out.println(date);

//        Date date = getEstDate();
//        LocalDateTime dateTime = date2LocalDateTime(date);
//        System.out.println(date);
//        System.out.println(dateTime);
//
//        date = getCttDate();
//        dateTime = date2LocalDateTime(date);
//        System.out.println(date);
//        System.out.println(dateTime);

//        LocalDateTime dateTime = getCttDateTime();
//        System.out.println(dateTime);
//        System.out.println(toOtherZone(dateTime, CTT_ZONE_ID, EST_ZONE_ID));

//        LocalDateTime dateTime = getEstDateTime();
//        System.out.println(estToCtt(dateTime));
//        System.out.println(cttToEst(getCttDateTime()));

//        System.out.println(getEstDate());
//        System.out.println(getCttDate());

//        System.out.println(firstDayOfCurrentWeek(getEstDate()));
//        System.out.println(firstDayOfLastWeek(getEstDate()));
//        System.out.println(lastDayOfLastWeek(getEstDate()));
//        System.out.println(firstDayOfCurrentMonth(getEstDate()));
//        System.out.println(firstDayOfLastMonth(getEstDate()));
//        System.out.println(lastDayOfLastMonth(getEstDate()));

//        System.out.println(getUtcDateTime());

        System.out.println(LocalDate.now().toString());
    }
}
