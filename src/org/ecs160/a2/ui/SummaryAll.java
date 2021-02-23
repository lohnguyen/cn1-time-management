package org.ecs160.a2.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

public class SummaryAll extends UpdateableContainer implements AppConstants {

    // label containers
    private Container taskContainer, sizeContainer, statsContainer;
    
    public SummaryAll () {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        // Tasks
        this.add(UIUtils.createLabel("Tasks", NATIVE_BOLD, 0x000000, 5.5f));
        this.taskContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(this.taskContainer);  

        // Sizes
        this.add(UIUtils.createLabel("Sizes", NATIVE_BOLD, 0x000000, 5.5f));
        this.sizeContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(this.sizeContainer);  

        // Stats
        this.add(UIUtils.createLabel("Statistics", NATIVE_BOLD, 0x000000, 5.5f));
        this.statsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(this.statsContainer);
    }

    // Updates task labels in the Tasks section
    // NOTE: could be more effecient with callbacks
    private void updateTaskLabels (List<Task> taskList) {
        List<Label> labels = UIUtils.getLabelsToUpdate(this.taskContainer, 
                                               taskList.size());
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i); // update the label w/ its
            Label label = labels.get(i); // corresponding task
            label.setText(" - " + (task.getTotalTime() / MILIS_TO_HOURS) +
                          " hours total for " + task.getTitle());
        }
    }

    // Updates size labels in the Size section
    private void updateSizeLabels (List<Task> taskList) {
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
        List<Label> labels = UIUtils.getLabelsToUpdate(this.sizeContainer, 
                                               sizeStatsMap.keySet().size());
        for (int i = 0; i < availableSizes.length; i++) {
            String size = (String) availableSizes[i];
            Label label = labels.get(i);
            label.setText(" - " + (sizeStatsMap.get(size) / MILIS_TO_HOURS) +
                          " hours total for " + size);
        }
    }

    // Update labels in the Statistics section
    // NOTE: can lessen calls by making the amount of labels constant
    private void updateStatsLabels (List<Task> taskList) {
        List<Label> labels = UIUtils.getLabelsToUpdate(this.statsContainer, 3);
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

    @Override
    public void updateContainer() {
        this.updateTaskLabels(Summary.taskList);
        this.updateSizeLabels(Summary.taskList);
        this.updateStatsLabels(Summary.taskList);
    }
}