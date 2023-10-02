package com.caster.security.utils.constants;

import java.time.format.DateTimeFormatter;

public class DateConstants {

    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YMDTHMS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String YMDTHMSSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String YMDTHMSZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String YMDTHMSxxx = "yyyy-MM-dd'T'HH:mm:ssxxx";

    /**
     * 日期格式化
     */
    public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern(yyyyMMdd);
    /**
     * 日期格式化
     */
    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(YMD);
    /**
     * 日期格式化成yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter YYYY_MM_DD_HH_mm_SS = DateTimeFormatter.ofPattern(YMDHMS);
    /**
     * 日期格式化成 yyyy-MM-dd'T'HH:mm:ss => 2022-08-03T14:28:07
     */
    public static final DateTimeFormatter YYYY_MM_DD_T_HH_mm_SS = DateTimeFormatter.ofPattern(YMDTHMS);
    /**
     * 日期格式化成 yyyy-MM-dd'T'HH:mm:ss => 2022-08-03T14:28:07.355
     */
    public static final DateTimeFormatter YYYY_MM_DD_T_HH_mm_SS_SSS = DateTimeFormatter.ofPattern(YMDTHMSSSS);
    /**
     * 日期格式化成 yyyy-MM-dd'T'HH:mm:ss => 2022-08-03T14:28:07Z
     */
    public static final DateTimeFormatter YYYY_MM_DD_T_HH_mm_SS_Z = DateTimeFormatter.ofPattern(YMDTHMSZ);
    /**
     * 日期格式化成 yyyy-MM-dd'T'HH:mm:ssxxx => 2022-08-03T14:28:07+08:00
     */
    public static final DateTimeFormatter YYYY_MM_DD_T_HH_mm_SSxxx = DateTimeFormatter.ofPattern(YMDTHMSxxx);
    /**
     * 日期格式化成yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter YYYY_MM_DD_HH_mm_SS_MS = DateTimeFormatter.ofPattern(YMDHMSSSS);
    /**
     * 日期格式化
     */
    public static final DateTimeFormatter DATE_FORMAT_NO_SLASH = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter DATETIME_FORMAT_NO_SLASH = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATETIME_FORMAT_SSS_NO_SLASH = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static final DateTimeFormatter TIME_FORMAT_NO_SLASH = DateTimeFormatter.ofPattern("HHmmss");
}
