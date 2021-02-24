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

        constructView();
    }

    private void constructView() {
        setToolbar();
        addTitle();
        addSize();
        addDescription();
        addTags();
        addTotalTime();
    }

    private void addTitle() {
        Label title = new Label(task.getTitle());
        Style style = title.getAllStyles();
        style.setFont(UIUtils.getTitleFont());
        style.setFgColor(COLOR_TITLE);
        add(title);
    }

    private void addSize() {
        Label size = new Label(task.getSize());
        add(size);
    }

    private void addHeader(String content) {
        add(UIUtils.createLabel(content, NATIVE_REGULAR, COLOR_TITLE,
                FONT_SIZE_SUB_TITLE));
    }

    private void addDescription() {
        addHeader("Description");

        add(new Label(task.getDescription()));

//        Button b = new Button("edit");
//        b.addActionListener(e -> {
//            task.setDescription("edited description");
//            Database.update(Task.OBJECT_ID, task);
//        });
//        add(b);
    }

    private void addTags() {
        addHeader("Tags");
        for (String t : task.getTags()) {
            add(new Label(t));
        }
    }

    private void addTotalTime() {
        addHeader("Total Time");
        Label totalTime = new Label(task.getTotalTimeStr());
        Style style = totalTime.getAllStyles();
        style.setFont(UIUtils.getTitleFont());
//        style.setFgColor(COLOR_TITLE);
        style.setAlignment(Component.CENTER);
        add(totalTime);
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