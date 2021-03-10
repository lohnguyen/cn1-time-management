package org.ecs160.a2.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtils {

    public static String durationStr(Duration duration) {
        if (duration == null) return "N/A";
        return duration.minusNanos(duration.getNano()).toString().substring(2);
    }

    public static String durationStr(long duration) {
        return durationStr(Duration.ofMillis(duration));
    }

    /**
     * Convert the total time as a formatted string
     * Reference: calculate time difference from milliseconds
     * https://stackoverflow.com/questions/4142313/convert-timestamp-in-
     * milliseconds-to-string-formatted-time-in-java/16520928#16520928
     * 
     * @return The formatted total time as "HR:MIN:SEC"
     */
    public static String timeAsString(long totalTime) {
        long second = totalTime / 1000 % 60;
        long minute = totalTime / (1000 * 60) % 60;
        long hour = totalTime / (1000 * 60 * 60);

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * Convert the total time to a formatted string for labels
     * Reference: calculate time difference from milliseconds
     * https://stackoverflow.com/questions/4142313/convert-timestamp-in-
     * milliseconds-to-string-formatted-time-in-java/16520928#16520928
     * 
     * @return The formatted total time as "HR hrs M min S s"
     */
    public static String timeAsLabelStr(long totalTime) {
        long second = totalTime / 1000 % 60;
        long minute = totalTime / (1000 * 60) % 60;
        long hour = totalTime / (1000 * 60 * 60);

        // build string (checking for zeroes)
        String str = String.format("%d sec", second);
        if (minute > 0) str = String.format("%d min ", minute) + str;
        if (hour > 0) str = String.format("%d hrs ", hour) + str;

        return str;
    }

    /**
     * Convert Date to LocalDateTime
     *
     * Reference: https://stackoverflow.com/questions/19431234/converting-
     * between-java-time-localdatetime-and-java-util-date
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * Convert LocalDateTime to Date
     *
     * Reference: https://stackoverflow.com/questions/36286922/convert-
     * localdatetime-to-date
     */
    public static Date toDate(LocalDateTime ldt) {
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
