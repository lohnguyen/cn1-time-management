package org.ecs160.a2.ui;

import com.codename1.components.Accordion;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.FontImage;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;

import java.util.ArrayList;
import java.util.List;


public class TaskList extends Container {

    private final ArrayList<Task> activeList;
    private final ArrayList<Task> inactiveList;

    public TaskList() {
        super(BoxLayout.y());
        this.setScrollableY(false);

        this.activeList = new ArrayList<>();
        this.inactiveList = new ArrayList<>();

        loadData();
    }

    /**
     * Creates/refreshes the task list
     */
    public void loadData() {
        this.activeList.clear();
        this.inactiveList.clear();
        List<Task> allTasks =
                (List) Database.readAll(Task.OBJECT_ID);
        this.inputTasks(allTasks);
        this.refreshContainer();
        this.configContainer();
    }

    /**
     * Splits the list of tasks into active and inactive tasks
     *
     * @param allTasks A list of every task in our database
     */
    private void inputTasks(List<Task> allTasks) {
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

    /**
     * Configures anything to do with the Container holding the task list
     */
    private void configContainer() {
        this.addPullToRefresh(() -> {
            loadData();
        });
    }

    /**
     * Refreshes the content of the taskList Container
     */
    private void refreshContainer() {
        this.removeAll();
        this.listTasks("Active Tasks", this.activeList);
        this.listTasks("Inactive Tasks", this.inactiveList);
        this.revalidate();
    }

    /**
     * Creates and adds the content to the Container for each list type given
     * by label
     *
     * @param label The label of the task, expects "active" or "inactive"
     * @param tasks The list of tasks that are of type <label>
     */
    private void listTasks(String label,
                           ArrayList<Task> tasks) {

//        Container taskTypeCont =
//                new Container(new FlowLayout(Component.CENTER));
//        Label taskTypeLabel = new Label(label);
//        if (label.equals("Active Tasks")) {
//            taskTypeLabel.setMaterialIcon(FontImage.MATERIAL_ALARM_ON);
//        } else {
//            taskTypeLabel.setMaterialIcon(FontImage.MATERIAL_ALARM_OFF);
//        }
//        taskTypeCont.addComponent(taskTypeLabel);
//        this.add(taskTypeCont);
//
//        if (tasks.size() == 0) {
//            Container emptyCont =
//                    new Container(new FlowLayout(Component.CENTER));
//            Label emptyLabel = new Label("no tasks");
//            emptyCont.addComponent(emptyLabel);
//            this.addComponent(emptyCont);
//        }
//
//        for (Task task : tasks) {
//            this.addComponent(new TaskCard(task));
//        }
//
        Accordion tasksAccordion = new Accordion();

        Container tasksContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        for (Task task : tasks) {
            tasksContainer.addComponent(new TaskCard(task));
        }
        tasksAccordion.setScrollableY(true);
        tasksContainer.setScrollableY(false);

        tasksAccordion.addContent(label, tasksContainer);
        this.addComponent(tasksAccordion);
    }


}