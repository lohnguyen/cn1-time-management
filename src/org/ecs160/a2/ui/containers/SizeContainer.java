package org.ecs160.a2.ui.containers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codename1.components.SpanLabel;
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

    private SpanLabel sizesLabel;
    private Label totalLabel;

    public SizeContainer () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.sizesLabel = UIUtils.createSpanLabel("",
                                                  NATIVE_LIGHT, 
                                                  COLOR_REGULAR,
                                                  FONT_SIZE_REGULAR);
        this.totalLabel = UIUtils.createLabel("Total Time: 0s",
                                              NATIVE_ITALIC_LIGHT,
                                              COLOR_REGULAR,
                                              FONT_SIZE_REGULAR);
        this.add(this.sizesLabel);
        this.add(this.totalLabel);
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
                         DurationUtils.timeAsLabelStr(sizeTime) +
                         " total for " + size;
        }

        // update total time label
        this.totalLabel.setText("Total Time: " +
                                DurationUtils.timeAsLabelStr(totalTime));

        // update the text of the internal label
        this.sizesLabel.setText(labelText);
        if (labelText.length() == 0) this.sizesLabel.setHidden(true);
        else this.sizesLabel.setHidden(false);
        this.forceRevalidate();
    }
}
