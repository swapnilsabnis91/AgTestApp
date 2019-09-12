package com.agstar.testapp.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class CommonUtils {
    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy-MM-dd HH:mma", cal).toString();
        return date;
    }
}
