package com.yanchao.blog.constant;

import java.io.File;

/**
 * 时间格式
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 30, 2020 2:39:05 PM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 30, 2020 2:39:05 PM
 */
public final class DateFormatConstant {

    public static final String YEAR = "yyyy";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String MINUTE = "mm";
    public static final String SECOND = "ss";
    public static final String NOTHING_BY_DATE = "yyyyMMdd";
    public static final String NOTHING_BY_TIME = "HHmmss";
    public static final String NOTHING_BY_DATETIME = "yyyyMMddHHmmss";
    public static final String STANDARD_BY_DATE = "yyyy-MM-dd";
    public static final String STANDARD_BY_TIME = "HH:mm:ss";
    public static final String STANDARD_BY_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FILE_BY_YEAR = new StringBuilder(File.separator).append(YEAR).append(File.separator)
            .toString();
    public static final String FILE_BY_MONTH = new StringBuilder(File.separator).append(YEAR).append(File.separator)
            .append(MONTH).append(File.separator).toString();
    public static final String FILE_BY_DAY = new StringBuilder(File.separator).append(YEAR).append(File.separator)
            .append(MONTH).append(File.separator).append(DAY).append(File.separator).toString();

    private DateFormatConstant() {
        throw new IllegalStateException("Utility class");
    }
}