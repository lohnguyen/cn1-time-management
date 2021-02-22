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
import com.codename1.ui.FontImage;
import com.codename1.ui.InfiniteContainer;
import org.ecs160.a2.models.Task;

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

//        Container taskTypeLabel = new Container(new FlowLayout());
//        MultiButton taskTypeButton = new MultiButton(label);
//        if (label.equals("Active Tasks")) {
//            FontImage.setMaterialIcon(taskTypeButton,
//                    FontImage.MATERIAL_ALARM_ON);
//        } else {
//            FontImage.setMaterialIcon(taskTypeButton,
//                    FontImage.MATERIAL_ALARM_OFF);
//        }
//        taskTypeLabel.add(taskTypeButton);
//        this.listContainer.add(taskTypeLabel);

        Container taskTypeCont =
                new Container(new FlowLayout(Component.CENTER));
        Label taskTypeLabel = new Label(label);
        if (label.equals("Active Tasks")) {
            taskTypeLabel.setMaterialIcon(FontImage.MATERIAL_ALARM_ON);
        } else {
            taskTypeLabel.setMaterialIcon(FontImage.MATERIAL_ALARM_OFF);
        }
        taskTypeCont.addComponent(taskTypeLabel);
        this.listContainer.add(taskTypeCont);

        if (tasks.size() == 0) {
            Container emptyCont =
                    new Container(new FlowLayout(Component.CENTER));
            Label emptyLabel = new Label("no tasks");
            emptyCont.addComponent(emptyLabel);
            this.listContainer.addComponent(emptyCont);
        }

        for (Task task : tasks) {

            MultiButton listItem = new MultiButton(task.getTitle());
            listItem.setTextLine2("details");
            listItem.addActionListener(ee ->
                    ToastBar.showMessage("Clicked: " + task.getTitle(),
                            FontImage.MATERIAL_ALARM_ON));
            this.listContainer.addComponent(listItem);
        }
    }
}
