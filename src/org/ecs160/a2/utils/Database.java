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
        Database.test();
    }

    public static int generateID(String key) {
        int id = readID(key);
        Log.p(String.valueOf(id));
        db.writeObject(key, id + 1);
        return id;
    }

    public static int readID(String key) {
        Integer id = (Integer) db.readObject(key);
        if (id == null) return 0;
        return id;
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
        if (Task.OBJECT_ID.equals(key)) updateTask(key, (Task) val);
    }

    public static Vector<Object> readAll(String key) {
        Vector<Object> vec = (Vector<Object>) db.readObject(key);
        if (vec == null) return new Vector<>();
        return vec;
    }

    public static void delete(String key, String id) {
        if (Task.OBJECT_ID.equals(key)) deleteTask(key, id);
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
//        Task t1 = new Task("test 1", "yee");
//        t1.start();
//        Task t2 = new Task("test 2", "yoo");
//        Task t3 = new Task("test 3", "yaa");
        String key = Task.OBJECT_ID;

        List<Task> tests = new ArrayList<>();
//        tests.add(t1);
//        tests.add(t2);

//        if (tests != null) {
//            for (Task t : tests) Log.p(t.getTitle() + t.getID());
//        }

//        t1.stop();
//        deleteAll(key);
//        Database.writeAll(key, (List) tests);
//        Database.write(key, t3);
        List<Task> vec = (List) readAll(key);

        if (vec != null) {
            for (Task t : vec)
                Log.p(t.getTitle() + " " + t.getID());
        }
    }

}
