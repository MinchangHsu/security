package com.caster.security.utils;




import com.caster.security.utils.constants.DateConstants;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Modified by Justin on 2019/5/1.
 * <p>
 * 日期，時間 工具
 */
public class DateTimeUtil {

    /**
     * 取得當前時間
     *
     * @return
     */
    public static LocalDateTime getNowLocal() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    /**
     * 取得當前時間
     *
     * @return
     */
    public static Date getNowDate() {
        return Date.from(getNowLocal().toInstant(ZoneOffset.of("+08:00")));
    }

    /**
     * 取得當前時間字串
     *
     * @return
     */
    public static String getNowString() {
        return getNowLocal().format(DateConstants.YYYY_MM_DD_HH_mm_SS);
    }

    /**
     * 取得當前時間字串
     *
     * @return
     */
    public static String getNowStringMs() {
        return getNowLocal().format(DateConstants.YYYY_MM_DD_HH_mm_SS_MS);
    }

    /**
     * Date 2 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertDate2LocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Timestamp 2 Date
     *
     * @param timestamp
     * @return
     */
    public static Date convertTimestamp2Date(Timestamp timestamp) {
        return Date.from(timestamp.toInstant());
    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.toInstant(ZoneOffset.of("+08:00"));
        Date date = Date.from(instant);
        return date;
    }

    /**
     * 日期格式化成yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateYYYY_MM_DD_HH_mm_SS(Date date) {
        if (date != null) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                    .format(DateConstants.YYYY_MM_DD_HH_mm_SS);
        } else {
            return "";
        }

    }

    /**
     * 日期格式化成yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date, DateTimeFormatter formatter) {
        if (date != null) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                    .format(formatter);
        } else {
            return "";
        }

    }

    /**
     * 日期格式化成yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date
     * @return
     */
    public static String formatDateYYYY_MM_DD_HH_mm_SS_MS(Date date) {
        if (date != null) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                    .format(DateConstants.YYYY_MM_DD_HH_mm_SS_MS);
        } else {
            return "";
        }

    }

    /***
     * 輸入datetime格式為yyyyMMddHHmmss的字串，parse為LocalDateTime
     * @param strDate
     * @return LocalDateTimne
     * @throws DateTimeParseException
     */
    public static LocalDateTime parseToLocalDate(String strDate,
												 DateTimeFormatter formatter) throws DateTimeParseException {
        LocalDateTime dateTime = null;
        dateTime = LocalDateTime.parse(strDate, formatter);
        return dateTime;

    }

    public static Date parseToDate(String strDate, DateTimeFormatter formatter) throws DateTimeParseException {
        LocalDateTime dateTime = null;
        dateTime = LocalDateTime.parse(strDate, formatter);
        return convertLocalDateTimeToDate(dateTime);

    }

    public static Date parseToDateNoTime(String strDate, DateTimeFormatter formatter) throws DateTimeParseException {
        LocalDateTime dateTime = null;
        dateTime = LocalDate.parse(strDate, formatter).atStartOfDay();
        return convertLocalDateTimeToDate(dateTime);
    }

    public static String formatToStartDate(LocalDate startDate){
        return startDate.format(DateConstants.YYYY_MM_DD) + " 00:00:00";
    }

    public static String formatToEndDate(LocalDate endDate){
        return endDate.format(DateConstants.YYYY_MM_DD) + " 23:59:59";
    }

    /**
     * 取得1970/01/01 00:00:00的時間
     * @return
     */
    public static Date getOriginDate() {
        LocalDateTime localDateTime = LocalDate.parse("1970/01/01", DateTimeFormatter.ofPattern("yyyy/MM/dd")).atStartOfDay();
        Instant instant = localDateTime.toInstant(ZoneOffset.of("+00:00"));
        Date date = Date.from(instant);
        return date;
    }

    /**
     * 取得n天的秒數. Ex. 1天 = 86400秒.
     * @param days
     * @return
     */
    public static long getSecondsOfDay(Integer days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getOriginDate());
        calendar.add(Calendar.DATE, days);
        return calendar.getTimeInMillis()/1000;
    }

    /**
     * 取得n小時的秒數. Ex. 1H = 3600秒.
     * @param hours
     * @return
     */
    public static long getSecondsOfHour(Integer hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getOriginDate());
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTimeInMillis()/1000;
    }

    /**
     * 取得兩個日期之前的天數
     * @param start
     * @param end
     * @return
     */
    public static long getDaysBetween(Calendar start, Calendar end){
        if (start.after(end)) {
            // swap dates so that startCalender is start and endCalender is end
            Calendar swap = start;
            start = end;
            end = swap;
        }

        int days = end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        int endYear = end.get(Calendar.YEAR);
        if (start.get(Calendar.YEAR) != endYear) {
            start = (Calendar) start.clone();
            do {
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);
                start.add(Calendar.YEAR, 1);
            } while (start.get(Calendar.YEAR) != endYear);
        }
        return days;
    }


    public static long getDaysBetween(LocalDateTime v1, LocalDateTime v2){
        Calendar start = Calendar.getInstance();
        start.set(v1.getYear(), v1.getMonthValue() - 1, v1.getDayOfMonth(), v1.getHour(), v1.getMinute(), v1.getSecond());
        Calendar end = Calendar.getInstance();
        end.set(v2.getYear(), v2.getMonthValue() - 1, v2.getDayOfMonth(), v2.getHour(), v2.getMinute(), v2.getSecond());
        return getDaysBetween(start, end);
    }

    /**
     * 取得兩個日期之前的天數 By Chrono Unit
     * @param start
     * @param end
     * @return
     */
    public static long getDaysBetweenByChronoUnit(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) {
            LocalDateTime v3 = start;
            start = end;
            end = v3;
        }
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 判斷日期是否有在區間(start ~ end)內
     * 如果毫秒數相同 before = false
     * @param start
     * @param end
     * @param target
     * @return
     */
    public static boolean isTimeBetween(Calendar start, Calendar end, Calendar target){
        return start.before(target) && target.before(end);
    }

    /**
     * Java8 LocalTime 與MySQL DateTime 精度不同導致數據查詢錯誤的問題,
     * Java 查看LocalTime的方法注釋,很清楚的寫明精度為小數點後9位,最大到.999999999.
     *
     * MySQL 5.7 之後的版本,在默認的秒精確度上,可以帶小數,最多帶6位小數，即可以精確到 microseconds (6 digits)
     *
     * 所以在取得end datetime 時 需要手動調整小數精度.
     * @param date
     * @return
     */
    public static LocalDateTime getStartDateWithTime(LocalDate date){
        if(Objects.isNull(date))
            return LocalDate.now().atTime(LocalTime.MIN);
        return date.atTime(LocalTime.MIN);
    }
    public static LocalDateTime getStartDateWithTime(){
        return getStartDateWithTime(null);
    }

    /**
     * Java8 LocalTime 與MySQL DateTime 精度不同導致數據查詢錯誤的問題,
     * Java 查看LocalTime的方法注釋,很清楚的寫明精度為小數點後9位,最大到.999999999.
     *
     * MySQL 5.7 之後的版本,在默認的秒精確度上,可以帶小數,最多帶6位小數，即可以精確到 microseconds (6 digits)
     *
     * 所以在取得end datetime 時 需要手動調整小數精度.
     * @param date
     * @return
     */
    public static LocalDateTime getEndDateWithTime(LocalDate date){
        if(Objects.isNull(date))
            return LocalDate.now().atTime(LocalTime.MAX.withNano(999999000));
        return date.atTime(LocalTime.MAX.withNano(999999000));
    }

    public static LocalDateTime getEndDateWithTime(){
        return getEndDateWithTime(null);
    }


    /**
     * 計算兩個時間差(ms)
     * @param v1
     * @param v2
     * @return
     */
    public static Long calculateLocalDateTime(LocalDateTime v1, LocalDateTime v2){
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        Long v1Ms = v1.atZone(zone).toInstant().toEpochMilli();
        Long v2Ms = v2.atZone(zone).toInstant().toEpochMilli();

        if(v1Ms > v2Ms)
            return v1Ms - v2Ms;
        return v2Ms - v1Ms;
    }


    public static int compare(Date startDate, Date endDate){
        int result = 0;
        Calendar startCal = Calendar.getInstance();
        Calendar endCal  = Calendar.getInstance();
        startCal.setTime(startDate);
        endCal.setTime(endDate);

        if (startCal.after(endCal))
            result = 1;
        else if (startCal.before(endCal))
            result = -1;
        else
            result = 0;

        return result;
    }

    public static LocalDateTime getLastMonthLastDays(){
        LocalDate ld = LocalDate.now().minusMonths(1);
        LocalTime lt = LocalTime.MAX.withNano(999999000);
        return LocalDateTime.of(
                LocalDate.now().minusMonths(1).withDayOfMonth(ld.getMonth().length(ld.isLeapYear())),
                lt);

    }

    public static void main(String[] args){

        System.out.println(getStartDateWithTime(getNowLocal().toLocalDate()).format(DateConstants.YYYY_MM_DD_HH_mm_SS));
        System.out.println(getEndDateWithTime(getNowLocal().toLocalDate()).format(DateConstants.YYYY_MM_DD_HH_mm_SS));
//        Date date = DateTimeUtil.getOriginDate();
//        System.out.println(date);
    }
}
