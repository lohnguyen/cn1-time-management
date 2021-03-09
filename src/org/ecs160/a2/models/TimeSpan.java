package org.ecs160.a2.models;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;

public class TimeSpan implements Externalizable {

    public static final String OBJECT_ID = "TimeSpan";

    static DateTimeFormatter dbFormatter = DateTimeFormatter.ISO_DATE_TIME;
    static DateTimeFormatter uiFormatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.SHORT);

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

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setStart(Date start) {
        this.start = toLocalDateTime(start);
    }

    public void setEnd(Date end) {
        this.end = toLocalDateTime(end);
    }

    public boolean isRunning() {
        return end == null;
    }

    /**
     * Stop the span if it has not been terminated. Set the end at timestamp
     *
     * @return whether the span was previously running
     */
    boolean stopSpan(LocalDateTime timestamp) {
        if (isRunning()) {
            end = timestamp;
            return true;
        }
        return false;
    }

    /**
     * Get the duration of the span. If still running, then use timestamp as
     * the stop time
     */
    Duration getDuration(LocalDateTime timestamp) {
        if (!isRunning()) return Duration.between(this.start, this.end);
        else return Duration.between(this.start, timestamp);
    }

    /**
     * Get the duration of all the timespans. If the last one is still
     * running, then use Duration.now() as the stop time
     */
    static Duration getTotalDuration(List<TimeSpan> timespans) {
        Duration duration = Duration.ZERO;
        for (int i = 0; i < timespans.size(); i++) {
            final TimeSpan span = timespans.get(i);
            if (i != timespans.size() - 1) {
                duration = duration.plus(span.getDuration(
                        timespans.get(i + 1).start));
            } else {
                duration = duration.plus(span.getDuration(LocalDateTime.now()));
            }
        }
        return duration;
    }

    /**
     * Get the time point string in format mm/dd/yy hh:mm AM/PM
     */
    static public String getTimeStr(LocalDateTime time) {
        if (time == null) return "Still In Progress";
        return uiFormatter.format(time);
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeUTF(start.format(dbFormatter), out);
        if (end == null) Util.writeUTF("", out);
        else Util.writeUTF(end.format(dbFormatter), out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        start = LocalDateTime.parse(Util.readUTF(in), dbFormatter);
        String endStr = Util.readUTF(in);
        if (endStr.equals("")) end = null;
        else end = LocalDateTime.parse(endStr, dbFormatter);
    }

    @Override
    public String getObjectId() {
        return OBJECT_ID;
    }
}
