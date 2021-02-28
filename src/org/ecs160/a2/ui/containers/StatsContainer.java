package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

public class StatsContainer extends UpdateableContainer implements AppConstants {

    public StatsContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
    }

    @Override
    public void updateContainer(List<Task> taskList) {
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
        labels.get(0).setText(" - " + (min / MILIS_TO_HOURS) + 
                              " hours minimum");
        labels.get(1).setText(" - " + (max / MILIS_TO_HOURS) + 
                              " hours maximum");
        labels.get(2).setText(" - " + (average / MILIS_TO_HOURS) + 
                              " hours average");
    }
}
