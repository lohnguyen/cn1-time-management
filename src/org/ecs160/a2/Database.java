package org.ecs160.a2;

import com.codename1.io.Log;
import com.codename1.io.Storage;
import com.codename1.io.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;

public class Database {
    static Storage db = Storage.getInstance();

    static void init() {
//        Util.register(Task.OBJECT_ID, Task.class);
        Util.register(Test.OBJECT_ID, Test.class);
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

    static Object read(String key) {
        return db.readObject(key);
    }

    static Vector<Object> readAll(String key) {
        return (Vector<Object>) db.readObject(key);
    }

    static void delete(String key) {
//        List<Test> vec = (List) readAll(key);
//        Predicate<Task> byName = person -> person.getAge() > 30;
//
//        var result = persons.stream().filter(byAge)
//                .collect(Collectors.toList());
    }

    static void deleteAll(String key) {
        db.deleteStorageFile(key);
    }

    static void test() {
        Test t1 = new Test("test 1", "yee");
        Test t2 = new Test("test 2", "yoo");
        Test t3 = new Test("test 3", "yaa");
        String key = "tests";

        List<Test> tests = new ArrayList<>();
        tests.add(t1);
        tests.add(t2);

        Database.writeAll(key, (List) tests);
        Database.write(key, t3);
        List<Test> vec = (List) readAll(key);

        if (vec != null) {
            for (Test t : vec) Log.p(t.getTitle());
        }
    }
}
