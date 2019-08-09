package com.appslelo.ebgsoldier.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {



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
    public static long convertTimeToMilisecond(String strDate) {

//        String myDate = "2014/10/29 18:10:45";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return date.getTime();

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



    public static boolean isExpire(String date){
        if(date.isEmpty() || date.trim().equals("")){
            return false;
        }else{
            SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            Date d;
            Date d1;
            String today=getToday();
            try {

                d = sdf.parse(date);
                d1 = sdf.parse(today);
                if(d1.compareTo(d) <0){// not expired
                    return false;
                }else if(d.compareTo(d1)==0){// both date are same
                    if(d.getTime() < d1.getTime()){// not expired
                        return false;
                    }else if(d.getTime() == d1.getTime()){//expired
                        return true;
                    }else{//expired
                        return true;
                    }
                }else{//expired
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    @SuppressLint("SimpleDateFormat")
    private static String getToday() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);


    }

    public static String getCurrentDateTime() {
        Date date = new Date();
//        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return new SimpleDateFormat(Constant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, Locale.UK).format(date);
    }



    public static long dateToMiliSeconds(String strDate){
        String myDate = strDate;
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss, Locale.UK);
        Date date = null;
        try {
            date = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }

}
