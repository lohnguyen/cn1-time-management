package org.ecs160.a2.utils;

import java.time.Duration;

public class DurationUtils {

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
}
