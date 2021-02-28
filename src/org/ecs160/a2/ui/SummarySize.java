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

public class SummarySize extends UpdateableContainer implements AppConstants {

    // label containers
    private UpdateableContainer tasks, stats;

    private Picker sizePicker;

    public SummarySize () {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        // picker
        this.sizePicker = new Picker();
        this.sizePicker.setType(Display.PICKER_TYPE_STRINGS);
        this.sizePicker.setStrings(Task.sizes.toArray(new String[Task.sizes.size()]));
        this.sizePicker.setSelectedStringIndex(1);
        this.sizePicker.addActionListener((e) -> askParentForUpdate());
        this.add(this.sizePicker);

        // Tasks
        this.add(UIUtils.createLabel("Tasks", NATIVE_BOLD, 0x000000, 5.5f));
        this.tasks = new TaskContainer();
        this.add(this.tasks);

        // Stats
        this.add(UIUtils.createLabel("Statistics", NATIVE_BOLD, 0x000000, 5.5f));
        this.stats = new StatsContainer();
        this.add(this.stats);
    }

    private List<Task> filterTaskList(List<Task> taskList, String size) {
        List<Task> returnList = new ArrayList<Task>();
        for (Task task : taskList) {
            if (task.getSize().equals(size)) returnList.add(task);
        }
        return returnList;
    }

    @Override
    public void updateContainer(List<Task> taskList) {   
        List<Task> filteredList;
        filteredList = filterTaskList(taskList, sizePicker.getSelectedString());
        this.tasks.updateContainer(filteredList);
        this.stats.updateContainer(filteredList);
    }
}