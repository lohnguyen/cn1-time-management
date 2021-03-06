package org.ecs160.a2.ui.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.TimeUtils;
import org.ecs160.a2.utils.UIUtils;

/**
 * Container that houses labels for Size statistics
 */
public class SizeContainer extends UpdateableContainer 
                           implements AppConstants {

    private SpanLabel sizesLabel;
    private Label totalLabel;

    // inner container constructor
    public SizeContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        sizesLabel = UIUtils.createSpanLabel("",
                                             NATIVE_LIGHT, 
                                             COLOR_REGULAR,
                                             FONT_SIZE_REGULAR);
        totalLabel = UIUtils.createLabel("Total Time: 0s",
                                         NATIVE_ITAL_LIGHT,
                                         COLOR_REGULAR,
                                         FONT_SIZE_REGULAR);
        add(sizesLabel);
        add(totalLabel);
    }

    // get size totals for each map available in the task list
    private Map<String, Long> getTaskSizeTotals (List<Task> taskList) {
        // use a map to keep track of the current totals for the sizes
        Map<String, Long> returnMap = new HashMap<String, Long>();

        // add up totals for the different sizes
        for (Task task : taskList) {
            if (task.getSize().equals("None")) continue; // no empty
            long sizeTime = task.getTotalTime();
            if (returnMap.containsKey(task.getSize())) {
                sizeTime += returnMap.get(task.getSize());
            }
            returnMap.put(task.getSize(), sizeTime);
        }

        return returnMap;
    }

    /**
     * Update the labels to reflect the statistics for each sizes
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        // variables used to update labels
        String labelText = "";
        long totalTime = 0L;

        // use a map to keep track of the current totals for the sizes
        Map<String, Long> sizeStatsMap = getTaskSizeTotals(taskList);

        // make sure label count is proper, then update the text
        Object[] availableSizes = sizeStatsMap.keySet().toArray();

        // build the new label text
        int numSizes = sizeStatsMap.keySet().size();
        for (int i = 0; i < numSizes; i++) {
            if (i > 0) labelText += "\n";

            String size = (String) availableSizes[i];
            long sizeTime = sizeStatsMap.get(size);

            // add to the total time
            totalTime += sizeTime;

            // update label text
            labelText += " - " +
                         TimeUtils.timeAsLabelStr(sizeTime) +
                         " total for " + size;
        }

        // update total time label
        totalLabel.setText("Total Time: " +
                           TimeUtils.timeAsLabelStr(totalTime));

        // update the text of the internal label
        sizesLabel.setText(labelText);
        if (labelText.length() == 0) sizesLabel.setHidden(true);
        else sizesLabel.setHidden(false);
        forceRevalidate();
    }
}
