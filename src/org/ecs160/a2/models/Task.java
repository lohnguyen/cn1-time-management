package org.ecs160.a2.models;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import org.ecs160.a2.utils.Database;

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

    // CN1 Storage fields for database
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
     * Default constructor for CN1 Storage
     */
    public Task() {
    }

    /**
     * Constructor where a title, description, size, and defaults tags need
     * to be specified.
     * NOTE: This constructor should only be used for creating a new
     * task to avoid unnecessary id generating
     *
     * @param title The title/name of the new Task
     * @param desc  A description of the new Task
     * @param size  The size of the new Task (@see #sizes)
     * @param tags  The list of tags for the new Task
     */
    public Task(String title, String desc, String size, List<String> tags) {
        // generate the id
        id = Database.generateID(COUNTER_ID);

        // basic task internals
        this.title = title;
        this.description = desc;
        this.size = size;
        this.tags = tags;

        // task time internals
        totalTime = 0L;
        timeSpans = new ArrayList<>();
        archived = false;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public List<String> getTags() {
        return tags;
    }

    public Boolean isArchived() { return archived; }

    public Task setTitle(String newTitle) {
        title = newTitle;
        return this;
    }

    public Task setDescription(String newDesc) {
        description = newDesc;
        return this;
    }

    public Task setSize(String newSize) {
        size = newSize;
        return this;
    }

    public Task setTimeSpans(List<TimeSpan> newTimeSpans) {
        timeSpans = newTimeSpans;
        totalTime = calculateTotalTime().toMillis();
        return this;
    }

    public Task setTags(List<String> newTags) {
        tags = newTags;
        return this;
    }

    public Task setArchived(Boolean newArchived) {
        archived = newArchived;
        return this;
    }

    /**
     * @return The list of accumulated time spans so far for this Task
     */
    public List<TimeSpan> getTimeSpans() {
        return timeSpans;
    }

    /**
     * Gets the most recently added time span/window
     *
     * @return The TimeSpan object of the most recently added time span
     */
    public TimeSpan getMostRecentTimeSpan() {
        if (timeSpans.size() == 0) return null;
        else return timeSpans.get(timeSpans.size() - 1);
    }

    /**
     * Checks whether the most recent time span is currently in progress
     *
     * @return Boolean that indicates whether the Task is running
     */
    public boolean isInProgress() {
        TimeSpan currentTimeSpan = getMostRecentTimeSpan();
        return currentTimeSpan != null && currentTimeSpan.isRunning();
    }

    /**
     * Starts the task according to the given LocalDateTime
     *
     * @param startTime The start time of the new TimeSpan
     */
    public void start(LocalDateTime startTime) {
        TimeSpan currentTimeSpan = getMostRecentTimeSpan();
        // task not running

        if (currentTimeSpan == null || (currentTimeSpan != null && 
                                        !currentTimeSpan.isRunning())) {
            timeSpans.add(new TimeSpan(startTime, null));
        }
    }

    /**
     * Starts the task at the current system time
     */
    public void start() {
        start(LocalDateTime.now());
    }

    /**
     * Stops the task according to the given LocalDateTime
     *
     * @param stopTime The stop time of the currently running TimeSpan
     */
    public void stop(LocalDateTime stopTime) {
        TimeSpan currentTimeSpan = getMostRecentTimeSpan();
        if (currentTimeSpan != null && currentTimeSpan.isRunning()) {
            currentTimeSpan.stopSpan(stopTime);
            totalTime = calculateTotalTime().toMillis();
        }
    }

    /**
     * Stops the task at the current system time
     */
    public void stop() {
        stop(LocalDateTime.now());
    }

    /**
     * Calculates the total time as a Duration for the current time spans
     * NOTE: could be public, or called every time total time is
     * 
     * @return The total time of the time spans as a Duration object
     */
    private Duration calculateTotalTime() {
        return TimeSpan.getTotalDuration(timeSpans);
    }

    /**
     * @return The total time of stopped time spans in miliseconds
     */
    public long getTotalTime() {
        return totalTime;
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
