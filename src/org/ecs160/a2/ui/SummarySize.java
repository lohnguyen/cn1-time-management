package org.ecs160.a2.ui;

import java.util.ArrayList;
import java.util.List;

import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

public class SummarySize extends UpdateableContainer implements AppConstants {

    // label containers
    private Container taskContainer, statsContainer;

    private Picker sizePicker;

    public SummarySize () {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        this.sizePicker = new Picker();
        this.sizePicker.setType(Display.PICKER_TYPE_STRINGS);
        this.sizePicker.setStrings(Task.sizes.toArray(new String[Task.sizes.size()]));
        this.sizePicker.setSelectedStringIndex(1);
        this.sizePicker.addActionListener((e) -> updateContainer());
        this.add(this.sizePicker);

        // Tasks
        this.add(UIUtils.createLabel("Tasks", NATIVE_BOLD, 0x000000, 5.5f));
        this.taskContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(this.taskContainer);

        // Stats
        this.add(UIUtils.createLabel("Statistics", NATIVE_BOLD, 0x000000, 5.5f));
        this.statsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(this.statsContainer);
    }

    // Updates task labels in the Tasks section
    // NOTE: could be more effecient with callbacks
    private void updateTaskLabels (List<Task> taskList) {
        List<Label> labels = UIUtils.getLabelsToUpdate(this.taskContainer, 
                                               taskList.size());
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i); // update the label w/ its
            Label label = labels.get(i); // corresponding task
            label.setText(" - " + (task.getTotalTime() / MILIS_TO_HOURS) +
                          " hours total for " + task.getTitle());
        }
    }

    // Update labels in the Statistics section
    // NOTE: can lessen calls by making the amount of labels constant
    private void updateStatsLabels (List<Task> taskList) {
        List<Label> labels = UIUtils.getLabelsToUpdate(this.statsContainer, 3);
        long min = -1L, average = -1L, max = -1L;

        // calculate the stats
        for (Task task : taskList) {
            long taskTime = task.getTotalTime();
            if (min < 0 || min > taskTime) min = taskTime;
            if (max < 0 || max < taskTime) max = taskTime;
            average += taskTime;
        }
        if(taskList.size() > 0) average /= taskList.size();

        // update the constant labels
        labels.get(0).setText(" - " + (min / MILIS_TO_HOURS) + 
                              " hours minimum");
        labels.get(1).setText(" - " + (max / MILIS_TO_HOURS) + 
                              " hours maximum");
        labels.get(2).setText(" - " + (average / MILIS_TO_HOURS) + 
                              " hours average");
    }

    private List<Task> filterTaskList(List<Task> taskList, String size) {
        List<Task> returnList = new ArrayList<Task>();
        for (Task task : taskList) {
            if (task.getSize().equals(size)) returnList.add(task);
        }
        return returnList;
    }

    @Override
    public void updateContainer() {   
        List<Task> taskList = filterTaskList(Summary.taskList, sizePicker.getSelectedString());
        this.updateTaskLabels(taskList);
        this.updateStatsLabels(taskList);
    }
}