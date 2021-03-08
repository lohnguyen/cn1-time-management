package org.ecs160.a2.ui;

import java.util.ArrayList;
import java.util.List;

import com.codename1.ui.Display;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.ui.containers.UpdateableContainer;
import org.ecs160.a2.ui.containers.TaskContainer;
import org.ecs160.a2.ui.containers.SizeContainer;
import org.ecs160.a2.ui.containers.StatsContainer;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

/**
 * The container that houses the Summary containers for different sizes
 */
public class SummaryTags extends UpdateableContainer implements AppConstants {

    // label containers
    private UpdateableContainer tasks, sizes, stats;

    // picker that allows the selection of tags
    private Picker tagsPicker;

    // tags list
    private List<String> tagsList;

    /**
     * Assemble the children of this container
     */
    public SummaryTags () {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        this.tagsList = new ArrayList<>();

        // size picker that updates everything on state change
        this.tagsPicker = new Picker();
        this.tagsPicker.setType(Display.PICKER_TYPE_STRINGS);
        this.tagsPicker.addActionListener((e) -> askParentForUpdate());
        this.add(this.tagsPicker);

        // Tasks
        this.add(UIUtils.createLabel("Tasks", NATIVE_BOLD, COLOR_TITLE,
                                     FONT_SIZE_SUB_TITLE));
        this.tasks = new TaskContainer();
        this.add(this.tasks);

        // Sizes
        this.add(UIUtils.createLabel("Sizes", NATIVE_BOLD, COLOR_TITLE,
                                     FONT_SIZE_SUB_TITLE));
        this.sizes = new SizeContainer();
        this.add(this.sizes);  

        // Stats
        this.add(UIUtils.createLabel("Statistics", NATIVE_BOLD, COLOR_TITLE,
                                     FONT_SIZE_SUB_TITLE));
        this.stats = new StatsContainer();
        this.add(this.stats);
    }

    // build the tags picker option for the given task list
    private void buildTagsPicker(List<Task> taskList) {
        // clear each time (inefficient)
        this.tagsList.clear();

        // 
        for (Task task : taskList) {
            for (String tag : task.getTags()) {
                this.tagsList.add(tag);
            }
        }

        // converto array and set the strings of the picker
        String[] tags = tagsList.toArray(new String[tagsList.size()]);
        this.tagsPicker.setStrings(tags);
    }

    // filter a task list for the specified tag
    private List<Task> filterTaskList(List<Task> taskList, String tag) {
        List<Task> returnList = new ArrayList<Task>();
        for (Task task : taskList) {
            if (task.getTags().contains(tag)) returnList.add(task);
        }
        return returnList;
    }

    /**
     * Update the sub containers after filtering the Task List for the current
     * size.
     * 
     * Note: Called each time a different picker option is selected and when the
     * page is refreshed
     */
    @Override
    public void updateContainer(List<Task> taskList) {   
        // build the picker selections
        this.buildTagsPicker(taskList);

        // filter the list
        List<Task> filteredList;
        filteredList = filterTaskList(taskList, tagsPicker.getSelectedString());

        // update the sub containers with the filtered list
        this.tasks.updateContainer(filteredList);
        this.sizes.updateContainer(filteredList);
        this.stats.updateContainer(filteredList);
    }
}