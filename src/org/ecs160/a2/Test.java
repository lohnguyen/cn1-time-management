package org.ecs160.a2;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test implements Externalizable {

    static final String OBJECT_ID = "Test";

    private String title, description, size;
    private long totalTime; // total time spent (excluding in progress)
    private List<String> tags;

    // allow for the construction of a Task based on a title and description
    public Test(String title, String description) {
        // basic task internals
        this.title = title;
        this.description = description;
        this.size = "";

        // task time internals
        this.totalTime = 0L;
        this.tags = new ArrayList<>();
    }

    // allow for the construction of a Task based on a title
    public Test(String title) {
        this(title, "");
    }

    public Test() {
        this("Task");
    }

    // for the basic task internals
    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSize() {
        return this.size;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setSize(String size) {
        this.size = size;
    }

    // for the time internals
    public long getTotalTime() {
        return this.totalTime;
    }

    public List<String> getTags() {
        return this.tags;
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeUTF(title, out);
        Util.writeUTF(description, out);
        Util.writeUTF(size, out);
        out.writeLong(totalTime);
        Util.writeObject(tags, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        title = Util.readUTF(in);
        description = Util.readUTF(in);
        size = Util.readUTF(in);
        totalTime = in.readLong();
        tags = (List<String>) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return OBJECT_ID;
    }
}
