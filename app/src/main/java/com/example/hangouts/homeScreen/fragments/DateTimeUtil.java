package com.example.hangouts.homeScreen.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    public static final String DATE_FORMAT="MM/dd/yy";
    public static final String TIME_FORMAT="HH:mm";

    public static String getDateString(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return dateFormat.format(date);
    }

    public static String getTimeString(Date date){
        SimpleDateFormat timeFormat=new SimpleDateFormat(TIME_FORMAT, Locale.US);
        return timeFormat.format(date);
    }
}
