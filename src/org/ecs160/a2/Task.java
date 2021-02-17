package org.ecs160.a2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {

   private String title, description, size;
   private long totalTime; // total time spent (excluding in progress)
   private List<TimeSpan> timeSpanList;
   private List<String> tags;

   // allow for the construction of a Task based on a title and description
   public Task (String title, String description) {
      // basic task internals
      this.title = title;
      this.description = description;
      this.size = "";

      // task time internals
      this.totalTime = 0L;
      this.timeSpanList = new ArrayList<TimeSpan>();
      this.tags = new ArrayList<String>();
   }

   // allow for the construction of a Task based on a title
   public Task (String title) {
      this(title, "");
   }

   public Task () {
      this("Task");
   }

   // for the basic task internals
   public String getTitle () { return this.title; }
   public String getDescription () { return this.description; }
   public String getSize () { return this.size; }

   public void setTitle (String title) { this.title = title; }
   public void setDescription (String desc) { this.description = desc; }
   public void setSize (String size) { this.size = size; }

   // for the time internals
   public long getTotalTime () { return this.totalTime; }
   // NOTE: may remove getTimeSpans in favor of wrapper functions for the list
   public List<TimeSpan> getTimeSpans () { return this.timeSpanList; }
   public List<String> getTags() { return this.tags; }

   // get the most recently added time span/window
   public TimeSpan getMostRecentTimeSpan () {
      return this.timeSpanList.get(timeSpanList.size() - 1);
   }

   // checks whether the most recent time span is currently in progress
   public boolean isInProgress () {
      TimeSpan currentTimeSpan = this.getMostRecentTimeSpan();
      // TODO return the check if most recent time span is in progress
   }

   public void start (LocalDateTime startTime) {
      this.timeSpanList.add(new TimeSpan(startTime));
   }

   public void stop (LocalDateTime stopTime) {
      TimeSpan currentTimeSpan = this.getMostRecentTimeSpan();
      // TODO check if most recent time span is in progress, throw
      // an exception if it's not.  If it is, update it's recent time
   }

   // NOTE: could be public, or called every time total time is
   private void calculateTotalTime () {
      Duration currentDuration = Duration.ZERO;
      // TODO loop through time span list, and add up the total time spent
   }
}