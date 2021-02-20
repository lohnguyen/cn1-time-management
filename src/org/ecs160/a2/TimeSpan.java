package org.ecs160.a2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TimeSpan {
    /**
     * The start time of a span. Non-null.
     */
    private LocalDateTime start;

    /**
     * The end time of a span. If null, it means this span is still running.
     */
    private LocalDateTime end;

    TimeSpan(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    LocalDateTime getStart() {
        return this.start;
    }

    LocalDateTime getEnd() {
        return this.end;
    }

    boolean isRunning() {
        return this.end == null;
    }

    /**
     * Stops the span if it has not been terminated. Set the end at Duration
     * .now()
     *
     * @return whether the span was previously running.
     */
    boolean stopSpan() {
        if (isRunning()) {
            this.end = LocalDateTime.now();
            return true;
        }
        return false;
    }

    /**
     * Stops the span if it has not been terminated. Set the end at timestamp.
     *
     * @return whether the span was previously running.
     */
    boolean stopSpan(LocalDateTime timestamp) {
        if (isRunning()) {
            this.end = timestamp;
            return true;
        }
        return false;
    }

    /**
     * Get the duration of the span. If still running, then use Duration.now
     * () as the stop time.
     *
     * @return whether the span was previously running.
     */
    Duration getDuration() {
        if (!isRunning()) {
            return Duration.between(this.start, this.end);
        } else {
            return Duration.between(this.start, LocalDateTime.now());
        }
    }

    /**
     * Get the duration of the span. If still running, then use timestamp as
     * the stop time.
     */
    Duration getDuration(LocalDateTime timestamp) {
        if (!isRunning()) {
            return Duration.between(this.start, this.end);
        } else {
            return Duration.between(this.start, timestamp);
        }
    }

    /**
     * Get the duration of all the timespans. If the last one is still
     * running, then use Duration.now() as the stop time.
     */
    static Duration getTotalDuration(List<TimeSpan> timespans) {
        Duration duration = Duration.ZERO;
        for (int i = 0; i < timespans.size(); i++) {
            final TimeSpan span = timespans.get(i);
            if (i != timespans.size() - 1) {
                duration =
                        duration.plus(span.getDuration(timespans.get(i + 1).start));
            } else {
                duration =
                        duration.plus(span.getDuration(LocalDateTime.now()));
            }
        }
        return duration;
    }
}