package org.ecs160.a2.ui;

import java.util.ArrayList;
import java.util.List;

import com.codename1.ui.Display;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.ui.containers.UpdateableContainer;
import org.ecs160.a2.ui.containers.TaskContainer;
import org.ecs160.a2.ui.containers.StatsContainer;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

/**
 * The container that houses the Summary containers for different sizes
 */
public class SummarySize extends UpdateableContainer implements AppConstants {

    // label containers
    private UpdateableContainer tasks, stats;

    // picker that allows the selection of sizes
    private Picker sizePicker;

    /**
     * Assemble the children of this container
     */
    public SummarySize () {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        // size picker that updates everything on state change
        String[] sizes = Task.sizes.toArray(new String[Task.sizes.size()]);
        this.sizePicker = new Picker();
        this.sizePicker.setType(Display.PICKER_TYPE_STRINGS);
        this.sizePicker.setStrings(sizes);
        this.sizePicker.setSelectedStringIndex(1); // default to "S"
        this.sizePicker.addActionListener((e) -> askParentForUpdate());
        this.add(this.sizePicker);

        // Tasks
        this.add(UIUtils.createLabel("Tasks", NATIVE_BOLD, COLOR_TITLE,
                                     FONT_SIZE_SUB_TITLE));
        this.tasks = new TaskContainer();
        this.add(this.tasks);

        // Stats
        this.add(UIUtils.createLabel("Statistics", NATIVE_BOLD, COLOR_TITLE,
                                     FONT_SIZE_SUB_TITLE));
        this.stats = new StatsContainer();
        this.add(this.stats);
    }

    // filter a task list for the specified size
    private List<Task> filterTaskList(List<Task> taskList, String size) {
        List<Task> returnList = new ArrayList<Task>();
        for (Task task : taskList) {
            if (task.getSize().equals(size)) returnList.add(task);
        }
        return returnList;
    }

    /**
     * Update the sub containers after filtering the Task List for the current
     * size
     */
    @Override
    public void updateContainer(List<Task> taskList) {   
        // filter the task list
        List<Task> filteredList;
        filteredList = filterTaskList(taskList, sizePicker.getSelectedString());

        // update the sub containers with that filtered list
        this.tasks.updateContainer(filteredList);
        this.stats.updateContainer(filteredList);
    }
}