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
       showTaskTitle();
       showDescription();
    }

    private void showTaskTitle() {
        Label title = new Label(task.getTitle());
        Style style = title.getAllStyles();
        style.setFont(UIUtils.getTitleFont());
        style.setFgColor(COLOR_TITLE);
        add(title);
    }

    private void showDescription() {
        Label description = new Label(task.getDescription());
        Style style = description.getAllStyles();
        style.setFont(UIUtils.getTitleFont());
        style.setFgColor(COLOR_TITLE);
        add(description);
    }

    private void setToolbar() {
        Toolbar tb = new Toolbar();
        setToolbar(tb);
        tb.setTitle("Task Details");

//        Label label = new Label("Task Details");
//        label.getAllStyles().setFont(UIUtils.getTitleFont());
//        tb.add(BorderLayout.CENTER, label);

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