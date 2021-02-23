package org.ecs160.a2.ui;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.UIUtils;

public class TaskDetail extends Form implements AppConstants {

    private final Form prev;
    private final Task task;

    TaskDetail(Task t) {
        super(BoxLayout.y());

        prev = Display.getInstance().getCurrent();
        task = t;

        setToolbar();
        setDetails();
    }

    private void setDetails() {
        Label title = new Label(task.getTitle());
        Style style = title.getAllStyles();
        style.setFont(UIUtils.getTitleFont());
        style.setFgColor(COLOR_TITLE);
        add(title);
    }

    private void setToolbar() {
        Toolbar tb = new Toolbar();
        setToolbar(tb);
        setTitle("Task Details");

        setBackCommand();
    }

    private void setBackCommand() {
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                prev.showBack();
            }
        };

        getToolbar().setBackCommand(back);
        Button button = getToolbar().findCommandComponent(back);
        button.setIcon(UIUtils.getBackIcon());
    }

}