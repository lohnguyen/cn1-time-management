package org.ecs160.a2.utils;

import com.codename1.io.Storage;
import com.codename1.io.Util;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.models.TimeSpan;

import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Database {

    static Storage db = Storage.getInstance();

    /**
     * Register objects to be saved in Storage
     */
    public static void init() {
        Util.register(Task.OBJECT_ID, Task.class);
        Util.register(TimeSpan.OBJECT_ID, TimeSpan.class);
    }

    /**
     * Remove all data from Storage, called when changes to db scheme are made
     */
    public static void reset() {
        deleteAll(Task.OBJECT_ID);
        deleteAll(Task.COUNTER_ID);
    }

    /**
     * Generate id for Task incrementally
     *
     * @param key The Storage's key of Task's id
     */
    public static int generateID(String key) {
        int id = readID(key);
        db.writeObject(key, id + 1);
        return id;
    }

    /**
     * Retrieve the next id for Task from Storage
     *
     * @param key The Storage's key of Task class
     */
    private static int readID(String key) {
        Integer id = (Integer) db.readObject(key);
        if (id == null) return 0;
        return id;
    }

    /**
     * Append object to the current list and write to Storage
     *
     * @param key The Storage's key of the objects
     * @param val The object to be written to Storage
     */
    public static void write(String key, Object val) {
        List<Object> vec = readAll(key);
        vec.add(val);
        writeAll(key, vec);
    }

    /**
     * Overwrite list of objects in Storage
     *
     * @param key  The Storage's key of the objects
     * @param vals The list of objects to be written to Storage
     */
    public static void writeAll(String key, List<Object> vals) {
        Vector<Object> vec = new Vector<>(vals);
        db.writeObject(key, vec);
    }

    /**
     * Replace a Task in Storage
     *
     * @param task A Task to replace a current Task with the same id
     */
    private static void updateTask(Task task) {
        List<Task> vec = (List) readAll(Task.OBJECT_ID);
        for (int i = 0; i < vec.size(); i++) {
            if (vec.get(i).getID() == task.getID()) {
                vec.set(i, task);
                break;
            }
        }
        writeAll(Task.OBJECT_ID, (List) vec);
    }

    /**
     * Replace a Task in Storage
     *
     * @param key The Storage's key of the object
     * @param val The updated object to be overwritten in Storage
     */
    public static void update(String key, Object val) {
        if (Task.OBJECT_ID.equals(key)) updateTask((Task) val);
    }

    /**
     * Read all objects of a class from Storage
     *
     * @param key The Storage's key of the objects to be retrieved
     *
     * @return List of objects saved in Storage with key
     */
    public static Vector<Object> readAll(String key) {
        Vector<Object> vec = (Vector<Object>) db.readObject(key);
        if (vec == null) return new Vector<>();
        return vec;
    }

    /**
     * Remove an object from Storage
     *
     * @param key The Storage's key of the object
     * @param id  The id of the object to be removed
     */
    public static void delete(String key, int id) {
        if (Task.OBJECT_ID.equals(key)) deleteTask(id);
    }

    /**
     * Remove a Task from Storage
     *
     * @param id The id of the Task to be removed
     */
    private static void deleteTask(int id) {
        List<Task> tasks = (List) readAll(Task.OBJECT_ID);
        Predicate<Task> byID = task -> task.getID() != id;
        tasks = tasks.stream().filter(byID).collect(Collectors.toList());
        writeAll(Task.OBJECT_ID, (List) tasks);
    }

    /**
     * Remove all objects from Storage
     *
     * @param key The Storage's key of the objects to be removed
     */
    public static void deleteAll(String key) {
        db.deleteStorageFile(key);
    }

}
