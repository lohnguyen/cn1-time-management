package org.ecs160.a2;

import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.io.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {
    static Storage db = Storage.getInstance();

    static void init() {
        Util.register(Task.OBJECT_ID, Task.class);
    }

    static void write(String key, Object val) {
        List<Object> vec = readAll(key);
        vec.add(val);
        writeAll(key, vec);
    }

    static void writeAll(String key, List<Object> vals) {
        Vector<Object> vec = new Vector<>(vals);
        db.writeObject(key, vec);
    }

    static Vector<Object> readAll(String key) {
        return (Vector<Object>) db.readObject(key);
    }

    static void deleteTask(String title) {
        List<Task> tasks = (List) readAll(Task.OBJECT_ID);
        Predicate<Task> byTitle = task -> !task.getTitle().equals(title);
        tasks = tasks.stream().filter(byTitle).collect(Collectors.toList());
        writeAll(Task.OBJECT_ID, (List) tasks);
    }

    static void deleteAll(String key) {
        db.deleteStorageFile(key);
    }

    static void test() {
        Task t1 = new Task("test 1", "yee");
        Task t2 = new Task("test 2", "yoo");
        Task t3 = new Task("test 3", "yaa");
        String key = Task.OBJECT_ID;

        List<Task> tests = new ArrayList<>();
        tests.add(t1);
        tests.add(t2);

//        deleteAll(key);
        deleteTask("test 3");
//        Database.writeAll(key, (List) tests);
//        Database.write(key, t3);
        List<Task> vec = (List) readAll(key);

        if (vec != null) {
            for (Task t : vec) Log.p(t.getTitle());
        }
    }
}