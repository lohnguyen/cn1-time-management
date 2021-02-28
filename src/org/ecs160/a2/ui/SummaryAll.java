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
    private UpdateableContainer tasks, sizes, stats;
    
    public SummaryAll () {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        // Tasks
        this.add(UIUtils.createLabel("Tasks", NATIVE_BOLD, 0x000000, 5.5f));
        this.tasks = new TaskContainer();
        this.add(this.tasks);  

        // Sizes
        this.add(UIUtils.createLabel("Sizes", NATIVE_BOLD, 0x000000, 5.5f));
        this.sizes = new SizeContainer();
        this.add(this.sizes);  

        // Stats
        this.add(UIUtils.createLabel("Statistics", NATIVE_BOLD, 0x000000, 5.5f));
        this.stats = new StatsContainer();
        this.add(this.stats);
    }

    @Override
    public void updateContainer() {
        this.tasks.updateContainer(Summary.taskList);
        this.sizes.updateContainer(Summary.taskList);
        this.stats.updateContainer(Summary.taskList);
    }
}