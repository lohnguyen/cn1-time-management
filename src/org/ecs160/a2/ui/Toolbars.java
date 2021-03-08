package org.ecs160.a2.ui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;

import java.io.IOException;

public class Toolbars {
    private Form current;
    private static Toolbar taskListToolbar;
    private static Toolbar summaryToolbar;

    public Toolbars(Form current) {
        this.current = current;
        taskListToolbar = createTaskListToolbar();
        summaryToolbar = createSummaryToolbar();
    }

    public static Toolbar getTaskListToolbar() {
        return taskListToolbar;
    }

    public static Toolbar getSummaryToolbar() {
        return summaryToolbar;
    }

    private Toolbar createBaseToolbar() {
        Toolbar toolbar = new Toolbar();
        current.setToolbar(toolbar);
        addNewTaskButton(toolbar);
        return toolbar;
    }

    private Toolbar createTaskListToolbar() {
        Toolbar toolbar = createBaseToolbar();
        toolbar.setTitle("Tasks");
        toolbar.addSearchCommand(TaskList::addSearch);
        return toolbar;
    }

    private Toolbar createSummaryToolbar() {
        Toolbar toolbar = createBaseToolbar();
        toolbar.setTitle("Summary");
        return toolbar;
    }

    private void addNewTaskButton(Toolbar toolbar) {
        Button addButton = new Button();
        addButton.addActionListener(e -> new TaskEditor(TaskEditor.TITLE_CREATE));
        setAddNewTaskButtonIcon(addButton);
        toolbar.addComponent(BorderLayout.EAST, addButton);
    }

    private void setAddNewTaskButtonIcon(Button button) {
        try {
            button.setIcon(Image.createImage("/addbutton.png").scaled(80, 80));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
