package com.mai.cat_chain.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maijuntian on 2018/6/26.
 */
public class Dateutils {

    static final String FORMAT = "yyyy-MM-dd";

    public static String getBeforeWeekDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        return new SimpleDateFormat(FORMAT).format(calendar.getTime());
    }

    public static String getNowDate() {

        return new SimpleDateFormat(FORMAT).format(new Date());
    }
}
