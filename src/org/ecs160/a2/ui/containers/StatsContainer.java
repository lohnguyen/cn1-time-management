package org.ecs160.a2.ui.containers;

import java.util.List;

import com.codename1.components.SpanLabel;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.TimeUtils;
import org.ecs160.a2.utils.UIUtils;

/**
 * Container that houses labels for calculated statistics
 */
public class StatsContainer extends UpdateableContainer 
                            implements AppConstants {

    private SpanLabel statsLabel;

    // inner container constructor
    public StatsContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.statsLabel = UIUtils.createSpanLabel("",
                                                  NATIVE_LIGHT, 
                                                  COLOR_REGULAR,
                                                  FONT_SIZE_REGULAR);
        this.add(this.statsLabel);
    }

    /**
     * Update the internal label to reflect the calculated statistics
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        String labelText = "";
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
        labelText += " - " + 
                     TimeUtils.timeAsLabelStr(min) +
                     " minimum\n" +
                     " - " + 
                     TimeUtils.timeAsLabelStr(max) +
                     " maximum\n" +
                     " - " + 
                     TimeUtils.timeAsLabelStr(average) +
                     " average";

        // update the text of the internal label
        this.statsLabel.setText(labelText);
        if (labelText.length() == 0) this.statsLabel.setHidden(true);
        else this.statsLabel.setHidden(false);
        this.forceRevalidate();
    }
}
