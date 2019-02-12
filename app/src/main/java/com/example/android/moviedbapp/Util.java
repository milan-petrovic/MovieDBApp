package com.example.android.moviedbapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
