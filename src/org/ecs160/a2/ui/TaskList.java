package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.FontImage;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.ui.TaskCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class TaskList extends Container {
    private Container listContainer;
    private ArrayList<Task> activeList;
    private ArrayList<Task> inactiveList;

    public TaskList() {
        this.listContainer = new Container(BoxLayout.y());
        this.listContainer.setScrollableY(true);
        this.activeList = new ArrayList<Task>();
        this.inactiveList = new ArrayList<Task>();
    }

    public Container get() {
        List<Task> allTasks =
                (List) Database.readAll(Task.OBJECT_ID);
        this.inputTasks(allTasks);
        this.refreshContainer();
        return this.listContainer;
    }

    public void inputTasks(List<Task> allTasks) {
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

    private void refreshContainer() {
        this.listContainer.removeAll();
        this.listTasks("Active Tasks", this.activeList);
        this.listTasks("Inactive Tasks", this.inactiveList);
    }

    private void listTasks(String label,
                                   ArrayList<Task> tasks) {

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
            this.listContainer.addComponent(new TaskCard(task));
        }
    }
}
