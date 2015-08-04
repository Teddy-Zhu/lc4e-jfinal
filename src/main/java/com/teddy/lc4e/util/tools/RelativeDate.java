package com.teddy.lc4e.util.tools;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by teddy on 2015/6/26.
 */
public class RelativeDate {

    private static final Logger LOGGER = Logger.getLogger(RelativeDate.class);
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;


    public static String format(Date date, Date now) {
        long delta = now.getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            if (seconds < 30) {
                return "now";
            } else {
                return (seconds <= 0 ? 1 : seconds) + " second ago";
            }
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + " minute ago";
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + " hour ago";
        }
        if (delta < 48L * ONE_HOUR) {
            return " yesterday";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + " day ago";
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + " month ago";
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + " year ago";
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
