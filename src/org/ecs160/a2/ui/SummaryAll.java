package org.ecs160.a2.ui;

import java.util.List;

import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.ui.containers.UpdateableContainer;
import org.ecs160.a2.ui.containers.TaskContainer;
import org.ecs160.a2.ui.containers.SizeContainer;
import org.ecs160.a2.ui.containers.StatsContainer;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

/**
 * The container/page that houses the statistics for all Tasks
 */
public class SummaryAll extends UpdateableContainer implements AppConstants {

    // label containers
    private UpdateableContainer tasks, sizes, stats;
    
    /**
     * Assemble the children of this container
     */
    public SummaryAll () {
        super(new BoxLayout(BoxLayout.Y_AXIS));

        // Tasks
        add(UIUtils.createLabel("Tasks", NATIVE_BOLD, COLOR_TITLE,
                                FONT_SIZE_SUB_TITLE));
        tasks = new TaskContainer();
        add(tasks);  

        // Sizes
        add(UIUtils.createLabel("Sizes", NATIVE_BOLD, COLOR_TITLE,
                                FONT_SIZE_SUB_TITLE));
        sizes = new SizeContainer();
        add(sizes);  

        // Stats
        add(UIUtils.createLabel("Statistics", NATIVE_BOLD, COLOR_TITLE,
                                FONT_SIZE_SUB_TITLE));
        stats = new StatsContainer();
        add(stats);
    }

    /**
     * Update the sub containers with the provided Task List
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        tasks.updateContainer(taskList);
        sizes.updateContainer(taskList);
        stats.updateContainer(taskList);
    }
}