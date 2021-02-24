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
}
