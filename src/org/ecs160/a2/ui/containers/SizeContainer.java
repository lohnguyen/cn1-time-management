package org.ecs160.a2.ui.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

public class SizeContainer extends UpdateableContainer implements AppConstants {

    public SizeContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
    }

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

        Object[] availableSizes = sizeStatsMap.keySet().toArray();
        List<Label> labels = UIUtils.getLabelsToUpdate(this, 
                                                       sizeStatsMap.keySet().size());
        for (int i = 0; i < availableSizes.length; i++) {
            String size = (String) availableSizes[i];
            Label label = labels.get(i);
            label.setText(" - " + (sizeStatsMap.get(size) / MILIS_TO_HOURS) +
                          " hours total for " + size);
        }
    }
}
