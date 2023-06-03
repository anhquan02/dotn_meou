package com.datn.meou.util;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdfS = new SimpleDateFormat("dd/MM/yyyy");

    public static DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static SimpleDateFormat sdtf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    public static SimpleDateFormat dateUpFile = new SimpleDateFormat("ddMMyyyyhhmmss");

    public static Date today() {
        return new Date();
    }

    public static String todayStr() {
        return sdf.format(today());
    }

    public static String todayStrTime() {
        return sdtf.format(today());
    }

    public static String dateUpFile() {
        return dateUpFile.format(today());
    }

    public static String formattedDate(Date date) {
        return date != null ? sdf.format(date) : todayStr();
    }

    public static Date setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        try {
            return ldf.parse(sdf.format(date));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
