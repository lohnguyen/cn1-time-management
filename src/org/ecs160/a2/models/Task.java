package org.ecs160.a2.models;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.DurationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A data model that keeps track of all data relevant for a single Task
 * that has multiple time windows/spans.
 */
public class Task implements Externalizable {

    // Database fields for this object
    public static final String OBJECT_ID = "Task";
    public static final String COUNTER_ID = "TaskCounter";

    // The possible list of sizes
    public static final List<String> sizes = Arrays.asList("S", "M", "L", "XL");

    private int id;
    private String title, description, size;
    private long totalTime; // total time spent (excluding in progress)
    private List<TimeSpan> timeSpans;
    private List<String> tags;
    private Boolean archived;

    /**
     * Constructor where a title, description, size, and defaults tags need
     * to be specified
     * 
     * @param title The title/name of the new Task
     * @param desc  A description of the new Task
     * @param size  The size of the new Task (@see #sizes)
     * @param tags  The list of tags for the new Task
     */
    public Task(String title, String desc, String size, List<String> tags) {
        // generate the id
        this.id = Database.generateID(COUNTER_ID);

        // basic task internals
        this.title = title;
        this.description = desc;
        this.size = size;

        // task time internals
        this.totalTime = 0L;
        this.timeSpans = new ArrayList<>();
        this.tags = tags;
        this.archived = false;
    }

    /**
     * Constructor where a title and description can be specified
     * 
     * @param title       The title/name of the new Task
     * @param description A description of the new Task
     */
    public Task(String title, String description) {
        // take advantage of previous constructor
        this(title, description, "None", new ArrayList<>());
    }

    /**
     * Constructor where a title can be specified
     * 
     * @param title The title/name of the new Task
     */
    public Task(String title) {
        this(title, "None");
    }

    /**
     * Default constructor for Tasks
     */
    public Task() {
        this("Task");
    }

    /**
     * @return The auto-generated Task ID
     */
    public int getID() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSize() {
        return this.size;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public Boolean isArchived() { return this.archived; }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public Task setDescription(String desc) {
        this.description = desc;
        return this;
    }

    public Task setSize(String size) {
        this.size = size;
        return this;
    }

    public Task setTimeSpans(List<TimeSpan> timeSpans) {
        this.timeSpans = timeSpans;
        this.totalTime = this.calculateTotalTime().toMillis();
        return this;
    }

    public Task setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public Task setArchived(Boolean archived) {
        this.archived = archived;
        return this;
    }

    /**
     * @return The list of accumulated time spans so far for this Task
     */
    public List<TimeSpan> getTimeSpans() {
        return this.timeSpans;
    }

    /**
     * Gets the most recently added time span/window
     *
     * @return The TimeSpan object of the most recently added time span
     */
    public TimeSpan getMostRecentTimeSpan() {
        if (timeSpans.size() == 0) return null;
        else return this.timeSpans.get(timeSpans.size() - 1);
    }

    /**
     * Checks whether the most recent time span is currently in progress
     *
     * @return Boolean that indicates whether the Task is running
     */
    public boolean isInProgress() {
        TimeSpan currentTimeSpan = this.getMostRecentTimeSpan();
        return currentTimeSpan != null && currentTimeSpan.isRunning();
    }

    /**
     * Starts the task according to the given LocalDateTime
     *
     * @param startTime The start time of the new TimeSpan
     */
    public void start(LocalDateTime startTime) {
        TimeSpan currentTimeSpan = this.getMostRecentTimeSpan();
        // task not running

        if (currentTimeSpan == null || (currentTimeSpan != null && 
                                        !currentTimeSpan.isRunning())) {
            this.timeSpans.add(new TimeSpan(startTime, null));
        } else {
            // TODO throw a custom exception if something is running
        }
    }

    /**
     * Starts the task at the current system time
     */
    public void start() {
        this.start(LocalDateTime.now());
    }

    /**
     * Stops the task according to the given LocalDateTime
     *
     * @param stopTime The stop time of the currently running TimeSpan
     */
    public void stop(LocalDateTime stopTime) {
        TimeSpan currentTimeSpan = this.getMostRecentTimeSpan();
        if (currentTimeSpan != null && currentTimeSpan.isRunning()) {
            currentTimeSpan.stopSpan(stopTime);
            this.totalTime = this.calculateTotalTime().toMillis();
        } else {
            // TODO throw a custom exception if nothing is running
        }
    }

    /**
     * Stops the task at the current system time
     */
    public void stop() {
        this.stop(LocalDateTime.now());
    }

    /**
     * Calculates the total time as a Duration for the current time spans
     * NOTE: could be public, or called every time total time is
     * 
     * @return The total time of the time spans as a Duration object
     */
    private Duration calculateTotalTime() {
        return TimeSpan.getTotalDuration(this.timeSpans);
    }

    /**
     * @return The total time of stopped time spans in miliseconds
     */
    public long getTotalTime() {
        return this.totalTime;
    }

    /**
     * Retrieve the current total time of the stopped time spans as a
     * formatted String
     *
     * Reference: calculate time difference from milliseconds
     * https://stackoverflow.com/questions/4142313/convert-timestamp-in-
     * milliseconds-to-string-formatted-time-in-java/16520928#16520928
     * 
     * @return The formatted total time as "HR:MIN:SEC"
     */
    public String getTotalTimeStr() {
        return DurationUtils.timeAsString(this.totalTime);
    }

    /**
     * Retrieve the current total time of the stopped time spans as a properly
     * formatted String for labels
     *
     * Reference: calculate time difference from milliseconds
     * https://stackoverflow.com/questions/4142313/convert-timestamp-in-
     * milliseconds-to-string-formatted-time-in-java/16520928#16520928
     * 
     * @return The formatted total time as "HR hrs M min S s"
     */
    public String getTotalTimeFormattedString() {
        return DurationUtils.timeAsLabelStr(this.totalTime);
    }

    @Override
    public int getVersion() {
        return 1;
    }

    /**
     * Write the internal data of a Task object into an output stream
     */
    @Override
    public void externalize(DataOutputStream out) throws IOException {
        out.writeInt(id);
        Util.writeUTF(title, out);
        Util.writeUTF(description, out);
        Util.writeUTF(size, out);
        out.writeLong(totalTime);
        Util.writeObject(tags, out);
        Util.writeObject(timeSpans, out);
        out.writeBoolean(archived);
    }

    /**
     * Set the internal data of a Task object from an input stream
     */
    @Override
    public void internalize(int ver, DataInputStream in) throws IOException {
        id = in.readInt();
        title = Util.readUTF(in);
        description = Util.readUTF(in);
        size = Util.readUTF(in);
        totalTime = in.readLong();
        tags = (List<String>) Util.readObject(in);
        timeSpans = (List<TimeSpan>) Util.readObject(in);
        archived = in.readBoolean();
    }

    @Override
    public String getObjectId() {
        return OBJECT_ID;
    }
}
