package org.ecs160.a2;

import java.util.ArrayList;
import java.util.List;

public class Task {

   private String title, description, size;
   private long totalTime; // total time spent (excluding in progress)
   private List<TimeSpan> timeSpanList;
   private List<String> tags;

   public Task (String title) {
      // basic task internals
      this.title = title;
      this.description = "";
      this.size = "";

      // task time internals
      this.totalTime = 0L;
      this.timeSpanList = new ArrayList<TimeSpan>();
      this.tags = new ArrayList<String>();
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
}