package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.InfiniteContainer;
import org.ecs160.a2.models.Task;

public class TaskList extends Container {
    public TaskList() {

    }

    public Container get() {
        return new InfiniteContainer() {
            @Override
            public Component[] fetchComponents(int index, int amount) {
                Component[] allTasks = new Component[20];

                for (int i = 0; i < allTasks.length; i++) {
//                    final int taskNum = i;
//                    MultiButton buttons = new MultiButton("Task " + taskNum);
//                    buttons.setTextLine2("details");
//                    FontImage.setMaterialIcon(buttons,
//                            FontImage.MATERIAL_ALARM_ON);
//                    buttons.addActionListener(ee ->
//                            ToastBar.showMessage("Clicked: " + taskNum,
//                                    FontImage.MATERIAL_ALARM_ON));
//                    allTasks[i] = buttons;
                    final Task testTask = new Task("test" + i);
                    allTasks[i] = new TaskCard(testTask);
                }


                return allTasks;
            }
        };
    }
}
