package org.ecs160.a2.ui;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.InfiniteContainer;
import com.codename1.ui.layouts.BoxLayout;
import org.ecs160.a2.models.Task;

public class TaskList extends Container {

    public TaskList() {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(createList());
    }

    public Container createList() {
        return new InfiniteContainer() {
            @Override
            public Component[] fetchComponents(int index, int amount) {
                Component[] allTasks = new Component[20];

                for (int i = 0; i < allTasks.length; i++) {
                    Task testTask = new Task("test" + i);
                    allTasks[i] = new TaskCard(testTask);
                }

                return allTasks;
            }
        };
    }
}
