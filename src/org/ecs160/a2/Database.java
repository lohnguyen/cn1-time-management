package org.ecs160.a2;

import com.codename1.io.Storage;
import com.codename1.io.Util;

public class Database {
    static Storage db = Storage.getInstance();

    static void init() {
//        Util.register("Task", Task.class);
    }

    static void write(String key, Object val) {
        db.writeObject(key, val);
    }

    static Object read(String key) {
        return db.readObject(key);
    }
}
