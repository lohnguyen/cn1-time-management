package org.ecs160.a2.ui;

import java.util.List;

import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

public class TaskContainer extends UpdateableContainer implements AppConstants {

    public TaskContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
    }

    @Override
    public void updateContainer() {
        List<Label> labels = UIUtils.getLabelsToUpdate(this, 
                                                       taskList.size());
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i); // update the label w/ its
            Label label = labels.get(i); // corresponding task
            label.setText(" - " + (task.getTotalTime() / MILIS_TO_HOURS) +
                          " hours total for " + task.getTitle());
        }
    }
}