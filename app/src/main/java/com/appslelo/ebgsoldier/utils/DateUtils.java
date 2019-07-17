package com.appslelo.ebgsoldier.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {


    public static long dateToMiliSeconds(String strDate){
        String myDate = strDate;
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }

    public static String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        return df.format(c);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFirstDateOfMonthOreo() {

        LocalDate todaydate = LocalDate.now();
        return String.valueOf(todaydate.withDayOfMonth(1));
    }
    public static String getFirstDateOfMonth() {
        Calendar aCalendar = Calendar.getInstance();

        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.SECOND, 0);

        Date firstDateOfCurrentMonth = aCalendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
       // sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        String dayFirst = sdf.format(firstDateOfCurrentMonth);
//        System.out.println(dayFirst);
        return sdf.format(firstDateOfCurrentMonth);
    }

    public static String getCurrentTime() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(Constant.TIME_FORMAT_hh_mm, Locale.getDefault());
        String currentTime = df.format(c);
        return currentTime;

    }
    public static Boolean compareDate(String valid_until) {

        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() > strDate.getTime();
    }
    public static Boolean compareDateEqual(String valid_until) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_dd_MMMM_yyyy, Locale.UK);
        Date strDate = null;
        try {
            strDate = sdf.parse(valid_until);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() < strDate.getTime();
    }
    public static String ddMMMMyyyy(String strDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date d = simpleDateFormat.parse(strDate);
            simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            return simpleDateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String hhmm(String strDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            Date d = simpleDateFormat.parse(strDate);
            simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
            return simpleDateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }




}
