package org.ecs160.a2.models;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import org.ecs160.a2.utils.TimeUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.*;
import java.util.Date;
import java.util.List;

public class TimeSpan implements Externalizable {

    // CN1 Storage field for database
    public static final String OBJECT_ID = "TimeSpan";

    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Default constructor for CN1 Storage
     */
    public TimeSpan() {
    }

    public TimeSpan(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setStart(Date start) {
        this.start = TimeUtils.toLocalDateTime(start);
    }

    public void setEnd(Date end) {
        this.end = TimeUtils.toLocalDateTime(end);
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
            TimeSpan span = timespans.get(i);
            LocalDateTime ldt = (i < timespans.size() - 1) ?
                    timespans.get(i + 1).start : LocalDateTime.now();
            duration = duration.plus(span.getDuration(ldt));
        }

        return duration;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeUTF(TimeUtils.timeAsDBString(start), out);
        if (end == null) Util.writeUTF("", out);
        else Util.writeUTF(TimeUtils.timeAsDBString(end), out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        start = TimeUtils.fromDBString(Util.readUTF(in));
        String endStr = Util.readUTF(in);
        end = (endStr.equals("")) ? null : TimeUtils.fromDBString(endStr);
    }

    @Override
    public String getObjectId() {
        return OBJECT_ID;
    }
}
