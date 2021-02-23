package org.ecs160.a2.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;

public class Summary extends Container implements AppConstants {

    // NOTE: this is temporary and will be changed to work with the
    // database later on!
    private static List<Task> taskList = new ArrayList<>();

    private void addLabel (String labelText, Font style, int color,
                           float fontSize) {
        Label label = new Label(labelText);
        int pixelSize = Display.getInstance().convertToPixels(fontSize);
        label.getAllStyles().setFont(style.derive(pixelSize,
                Font.STYLE_PLAIN));
        label.getAllStyles().setFgColor(color);
        this.add(label);
    }

    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setScrollableY(true);

        // temporary Tasks for the summary
        taskList.add(new Task("Task 1"));
        taskList.add(new Task("Task 2"));
        taskList.get(0).start(LocalDateTime.of(2021, 2, 21, 5, 0));
        taskList.get(0).stop(LocalDateTime.of(2021, 2, 21, 7, 0));

        this.addLabel("Summary", NATIVE_BOLD, 0x000000, 8.0f);

        // TODO add other pages (by size and all task summary)

        this.addLabel("Tasks", NATIVE_BOLD, 0x000000, 5.5f);

        for (Task task : taskList) {
            this.addLabel(" - " + (task.getTotalTime() / 3600000) +
                            " hours total for " + task.getTitle(),
                    NATIVE_LIGHT, 0x000000, 3.0f);
        }


        this.addLabel("Sizes", NATIVE_BOLD, 0x000000, 5.5f);
        // TODO sizes list


        this.addLabel("Statistics", NATIVE_BOLD, 0x000000, 5.5f);
        // TODO stats list


    }
}