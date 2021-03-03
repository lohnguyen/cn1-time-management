package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.DurationUtils;
import org.ecs160.a2.utils.UIUtils;

/**
 * Container that houses labels for the times of Tasks
 */
public class TaskContainer extends UpdateableContainer 
                           implements AppConstants {

    public TaskContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
    }

    /**
     * Update the labels to reflect the times of the given Tasks
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        // get a list of labels for the specified number of tasks
        List<Label> labels = UIUtils.getLabelsToUpdate(this, 
                                                       taskList.size());
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i); // update the label w/ its
            Label label = labels.get(i); // corresponding task
            label.setText(" - " + 
                          DurationUtils.timeAsLabelStr(task.getTotalTime()) +
                          " total for " + task.getTitle());
        }
    }
}
