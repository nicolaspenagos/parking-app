/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    // -------------------------------------
    // Constants
    // -------------------------------------
    public final static long DAY = 86400000;
    public final static long HOUR = 3600000;
    public final static long MINUTE = 60000;
    public final static long SECOND = 1000;

    // -------------------------------------
    // Methods
    // -------------------------------------
    public static String getTimeDayHourMinuteSecond(long start, long end){

        String time = "";

        long dif = end-start;

        long days = dif/DAY;
        dif = dif - (days*DAY);

        long hours = dif/HOUR;
        dif = dif - (hours*HOUR);

        long minutes = dif/MINUTE;
        dif = dif - (minutes*MINUTE);

        long seconds = dif/SECOND;

        time = days +" d "+hours+" h "+minutes+" min "+seconds+" s";

        return time;

    }

    public static String toDate(long milliseconds){

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        return sdf.format(resultdate);

    }

    public static String getCurrentTime(){

        long milliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date resultdate = new Date(milliseconds);
        return sdf.format(resultdate);

    }

    public static int timeToHours(long start, long end){

        long dif = end-start;
        long time = dif/HOUR;

        time = (dif%HOUR != 0) ? time+1 : time;

        return (int)time;

    }

}
