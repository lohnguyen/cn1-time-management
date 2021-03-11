package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.TimeUtils;
import org.ecs160.a2.utils.UIUtils;

/**
 * Container that houses labels for the times of Tasks
 */
public class TaskContainer extends UpdateableContainer 
                           implements AppConstants {

    private SpanLabel tasksLabel;
    private Label totalLabel;

    // inner container constructor
    public TaskContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        tasksLabel = UIUtils.createSpanLabel("",
                                             NATIVE_LIGHT, 
                                             COLOR_REGULAR,
                                             FONT_SIZE_REGULAR);
        totalLabel = UIUtils.createLabel("Total Time: 0s",
                                         NATIVE_ITAL_LIGHT,
                                         COLOR_REGULAR,
                                         FONT_SIZE_REGULAR);
        add(tasksLabel);
        add(totalLabel);
    }

    /**
     * Update the label to reflect the times of the given Tasks
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        // variables used to update labels
        String labelText = "";
        long totalTime = 0L;

        // build the text for the internal label
        for (int i = 0; i < taskList.size(); i++) {
            // if second task or later, add a line break
            if (i > 0) labelText += "\n";

            // get current task
            Task task = taskList.get(i);

            // add to the total time
            totalTime += task.getTotalTime();

            // update label text
            labelText += " - " + 
                         TimeUtils.timeAsLabelStr(task.getTotalTime()) +
                         " total for " + task.getTitle();
        }

        // update total time label
        this.totalLabel.setText("Total Time: " +
                                TimeUtils.timeAsLabelStr(totalTime));

        // update the text of the internal label
        this.tasksLabel.setText(labelText);
        if (labelText.length() == 0) this.tasksLabel.setHidden(true);
        else this.tasksLabel.setHidden(false);
        this.forceRevalidate();
    }
}
