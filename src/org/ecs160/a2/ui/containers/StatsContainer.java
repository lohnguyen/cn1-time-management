package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.DurationUtils;
import org.ecs160.a2.utils.UIUtils;

/**
 * Container that houses labels for calculated statistics
 */
public class StatsContainer extends UpdateableContainer 
                            implements AppConstants {

    public StatsContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
    }

    /**
     * Update the internal labels to reflect the calculated statistics
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        // make sure label count is correct
        List<Label> labels = UIUtils.getLabelsToUpdate(this, 3);
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
        labels.get(0).setText(" - " + 
                              DurationUtils.timeAsLabelStr(min) +
                              " minimum");
        labels.get(1).setText(" - " + 
                              DurationUtils.timeAsLabelStr(max) +
                              " maximum");
        labels.get(2).setText(" - " + 
                              DurationUtils.timeAsLabelStr(average) +
                              " average");
    }
}