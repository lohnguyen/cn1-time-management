package org.ecs160.a2;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

class DurationUtils {
    static String prettyPrint(Duration duration) {
        if (duration == null) return "N/A";
        return duration.minusNanos(duration.getNano()).toString().substring(2);
    }

    static String prettyPrint(long duration) {
        return prettyPrint(Duration.ofSeconds(duration));
    }
}