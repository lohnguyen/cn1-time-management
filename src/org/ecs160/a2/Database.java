package org.ecs160.a2;

import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.io.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Database {
    static Storage db = Storage.getInstance();

    static void init() {
        Util.register(Task.OBJECT_ID, Task.class);
        Util.register(Test.OBJECT_ID, Test.class);
    }

    static void write(String key, Object val) {
        db.writeObject(key, val);
    }

    static void writeAll(String key, List<Object> vals) {
        Vector<Object> vec = new Vector<>(vals);
        db.writeObject(key, vec);
    }

    static Object read(String key) {
        return db.readObject(key);
    }

    static Vector<Object> readAll(String key) {
        return (Vector<Object>) db.readObject(key);
    }

    static void test() {
        Test t1 = new Test("test 1", "yee");
        Test t2 = new Test("test 2", "yoo");
        String key = "tests";

        List<Test> tests = new ArrayList<>();
        tests.add(t1);
        tests.add(t2);

        writeAll(key, (List) tests);
        List<Test> vec = (List) readAll(key);

        if (vec != null) {
            for (Test t : vec) Log.p(t.getTitle());
        }
    }
}
