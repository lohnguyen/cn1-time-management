package org.ecs160.a2.models;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class TimeSpan implements Externalizable {

    public static final String OBJECT_ID = "TimeSpan";

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * The start time of a span. Non-null.
     */
    private LocalDateTime start;

    /**
     * The end time of a span. If null, it means this span is still running.
     */
    private LocalDateTime end;

    public TimeSpan(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public TimeSpan() {
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
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

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeUTF(start.format(formatter), out);
        Util.writeUTF(end.format(formatter), out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        start = LocalDateTime.parse(Util.readUTF(in), formatter);
        end = LocalDateTime.parse(Util.readUTF(in), formatter);
    }

    @Override
    public String getObjectId() {
        return OBJECT_ID;
    }
}
