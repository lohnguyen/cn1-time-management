package org.ecs160.a2.ui.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.DurationUtils;
import org.ecs160.a2.utils.UIUtils;

/**
 * Container that houses labels for Size statistics
 */
public class SizeContainer extends UpdateableContainer 
                           implements AppConstants {

    public SizeContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
    }

    /**
     * Update the labels to reflect the statistics for each sizes
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        // use a map to keep track of the current totals for the sizes
        Map<String, Long> sizeStatsMap = new HashMap<String, Long>();

        // add up totals for the different sizes
        for (Task task : taskList) {
            if (task.getSize().equals("None")) continue; // no empty
            long sizeTime = task.getTotalTime();
            if (sizeStatsMap.containsKey(task.getSize())) {
                sizeTime += sizeStatsMap.get(task.getSize());
            }
            sizeStatsMap.put(task.getSize(), sizeTime);
        }

        // make sure label count is proper, then update the text
        Object[] availableSizes = sizeStatsMap.keySet().toArray();

        int numSizes = sizeStatsMap.keySet().size();
        List<Label> labels = UIUtils.getLabelsToUpdate(this, 
                                                       numSizes);
        for (int i = 0; i < numSizes; i++) {
            String size = (String) availableSizes[i];
            Label label = labels.get(i);
            long sizeTime = sizeStatsMap.get(size);

            // update label text
            label.setText(" - " +
                          DurationUtils.timeAsLabelStr(sizeTime) +
                          " total for " + size);
        }
    }
}
