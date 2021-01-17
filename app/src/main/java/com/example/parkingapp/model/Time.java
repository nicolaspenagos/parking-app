/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp.model;

public class Time {

    // -------------------------------------
    // Constants
    // -------------------------------------
    public final static long DAY = 86400000;
    public final static long HOUR = 3600000;
    public final static long MINUTE = 60000;
    public final static long SECOND = 1000;

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

}
