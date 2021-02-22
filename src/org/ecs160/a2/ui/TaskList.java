package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;

import java.util.ArrayList;

public class TaskList extends Container {
    private Database database;
    private Container listContainer;
    private ArrayList<Task> activeList;
    private ArrayList<Task> inactiveList;

    public TaskList(Database db) {
        Database database = db;
        this.listContainer = new Container(BoxLayout.y());
        this.listContainer.setScrollableY(true);
        this.activeList = new ArrayList<Task>();
        this.inactiveList = new ArrayList<Task>();
    }

    public Container getContainer(ArrayList<Task> allTasks) {
        this.inputTasks(allTasks);
        this.refreshContainer();
        return this.listContainer;
    }

    public Container refreshContainer() {
//        ArrayList<Task> allTasks = this.database.readAll(Task.OBJECT_ID);
//        this.inputTasks(allTasks);

        this.listContainer.removeAll();
        this.listTasks("Active Tasks", this.activeList);
        this.listTasks("Inactive Tasks", this.inactiveList);
        return this.listContainer;
    }

    public void inputTasks(ArrayList<Task> allTasks) {
        ArrayList<Task> activeTasks = new ArrayList<>();
        ArrayList<Task> inactiveTasks = new ArrayList<>();

        for (Task task : allTasks) {
            if (task.isInProgress()) {
                this.activeList.add(task);
            } else {
                this.inactiveList.add(task);
            }
        }
    }

    private void listTasks(String label,
                                   ArrayList<Task> tasks) {

        Container taskTypeLabel = new Container(new FlowLayout());
        MultiButton taskTypeButton = new MultiButton(label);
        if (label.equals("Active Tasks")) {
            FontImage.setMaterialIcon(taskTypeButton,
                    FontImage.MATERIAL_ALARM_ON);
        } else {
            FontImage.setMaterialIcon(taskTypeButton,
                    FontImage.MATERIAL_ALARM_OFF);
        }
        taskTypeLabel.add(taskTypeButton);
        this.listContainer.add(taskTypeLabel);

//        Container lab = new Container(new FlowLayout(Component.CENTER));
//        Label l = new Label(label);
//        lab.addComponent(l);
//        this.listContainer.add(lab);

        if (tasks.size() == 0) {
            Container empty = new Container(new FlowLayout(Component.CENTER));
            Label e = new Label("no tasks");
            empty.addComponent(e);
            this.listContainer.addComponent(empty);
        }

        for (Task task : tasks) {

            MultiButton listItem = new MultiButton("Task " + task.getTitle());
            listItem.setTextLine2("details");
            listItem.addActionListener(ee ->
                    ToastBar.showMessage("Clicked: " + task.getTitle(),
                            FontImage.MATERIAL_ALARM_ON));

//            Container listItem = new Container(new BorderLayout());
//            listItem.add(BorderLayout.WEST, new Label(task.getTitle()));
//
//            Button b = new Button("BUTTON");
//            b.addActionListener(i ->
//                            ToastBar.showMessage("Clicked: " + task.getTitle(),
//                                    FontImage.MATERIAL_ALARM_ON));
//
//            listItem.add(BorderLayout.EAST, b);
            this.listContainer.addComponent(listItem);
        }
    }
}
