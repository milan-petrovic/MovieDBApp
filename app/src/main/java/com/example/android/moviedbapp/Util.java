package com.example.android.moviedbapp;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static String getYearFromDate(String sDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(sDate);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            String myDate = sdf2.format(date);
            return myDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertRuntime(Integer time) {

        int hours = time / 60;
        int minutes = time % 60;

        return hours + " hrs " + minutes + " mins" ;
    }

    public static String getYearMonthDay(String sDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(sDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DateFormatSymbols symbols = new DateFormatSymbols(Locale.ENGLISH);
            String[] monthNames = symbols.getMonths();
            return day + " " + monthNames[month] + " " + year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


}
