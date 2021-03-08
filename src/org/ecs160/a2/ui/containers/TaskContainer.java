package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.components.SpanLabel;
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

    private SpanLabel tasksLabel;

    public TaskContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.tasksLabel = UIUtils.createSpanLabel("",
                                                  NATIVE_LIGHT, 
                                                  COLOR_REGULAR,
                                                  FONT_SIZE_REGULAR);
        this.add(this.tasksLabel);
    }

    /**
     * Update the label to reflect the times of the given Tasks
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        String labelText = "";
        for (int i = 0; i < taskList.size(); i++) {
            if (i > 0) labelText += "\n";
            Task task = taskList.get(i); // update the label w/ its
            labelText += " - " + 
                         DurationUtils.timeAsLabelStr(task.getTotalTime()) +
                         " total for " + task.getTitle();
        }
        this.tasksLabel.setText(labelText);
        if (labelText.length() == 0) this.tasksLabel.setHidden(true);
        else this.tasksLabel.setHidden(false);
        this.forceRevalidate();
    }
}
