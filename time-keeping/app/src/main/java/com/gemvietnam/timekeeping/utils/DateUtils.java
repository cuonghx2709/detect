package com.gemvietnam.timekeeping.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {
    public static String getTimeString(Long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }
    public static String getDayString(Long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }
    public static String getTotal(Double total){
        NumberFormat format = new DecimalFormat("#0.00");
        return format.format(total);
    }
    public static int getCurrentMonth(){
        return new Date().getMonth();
    }
    public static int getCurentYear(){
        return new Date().getYear();
    }
    public static int getCurrentDay(){
        return new Date().getDay();
    }
    public static int getEqualDate(Long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        return new Date(timeStamp).getDay();
    }
    public static Date getCurrentDate(){
        return new Date();
    }

    public static long getLongCurrentDate(){
        return System.currentTimeMillis()/1000;
    }


    public static String getDateTimeString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getDateTimeString(Long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }

    public static String getDateYearString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        return simpleDateFormat.format(date);
    }

    public static long convertDateToTimeStamp(Date date){
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.getTime();
    }

    public static String convertTimeStampToDate(long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }

    public static String convertTimeStampToDateTime(long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }

    public static int getDayOfWeek(long timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("F");
        Date date = new Date(timeStamp);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getMonth(long timeStamp){
        Date date = new Date(timeStamp);
        return date.getMonth();
    }

    public static String getStringDateAddLeave(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }


}
