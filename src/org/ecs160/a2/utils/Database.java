package org.ecs160.a2.utils;

import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import org.ecs160.a2.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {
    static Storage db = Storage.getInstance();

    public static void init() {
        Util.register(Task.OBJECT_ID, Task.class);
    }

    public static void write(String key, Object val) {
        List<Object> vec = readAll(key);
        vec.add(val);
        writeAll(key, vec);
    }

    public static void writeAll(String key, List<Object> vals) {
        Vector<Object> vec = new Vector<>(vals);
        db.writeObject(key, vec);
    }

    public static void updateTask(String key, Task task) {
        List<Task> vec = (List) readAll(key);
        for (int i = 0; i < vec.size(); i++) {
            if (vec.get(i).hasSameTitle(task)) {
                vec.set(i, task);
                break;
            }
        }
        writeAll(key, (List) vec);
    }

    public static void update(String key, Object val) {
        switch (key) {
            case Task.OBJECT_ID:
                updateTask(key, (Task) val);
                break;
        }
    }

    public static Vector<Object> readAll(String key) {
        Vector<Object> vec = (Vector<Object>) db.readObject(key);
        if (vec == null) return new Vector<>();
        return vec;
    }

    public static void delete(String key, String id) {
        switch (key) {
            case Task.OBJECT_ID:
                deleteTask(key, id);
                break;
        }
    }

    public static void deleteTask(String key, String title) {
        List<Task> tasks = (List) readAll(key);
        Predicate<Task> byTitle = task -> !task.getTitle().equals(title);
        tasks = tasks.stream().filter(byTitle).collect(Collectors.toList());
        writeAll(key, (List) tasks);
    }

    public static void deleteAll(String key) {
        db.deleteStorageFile(key);
    }

    public static void test() {
        Task t1 = new Task("test 1", "yee");
        t1.start();
        Task t2 = new Task("test 2", "yoo");
        Task t3 = new Task("test 3", "yaa");
        String key = Task.OBJECT_ID;

        List<Task> tests = new ArrayList<>();
        tests.add(t1);
        tests.add(t2);

        t1.stop();
//        deleteAll(key);
//        Database.writeAll(key, (List) tests);
//        Database.write(key, t3);
        List<Task> vec = (List) readAll(key);

        if (vec != null) {
            for (Task t : vec) Log.p(t.getTitle());
        }
    }

}
