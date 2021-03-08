package org.ecs160.a2.ui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;

import java.io.IOException;

public class AppToolbars {
    private Form current;
    private static Toolbar taskListToolbar;
    private static Toolbar summaryToolbar;

    public AppToolbars(Form current) {
        this.current = current;
        taskListToolbar = createTaskListToolbar();
        summaryToolbar = createSummaryToolbar();
    }

    /**
     * @return The taskList Toolbar object
     */
    public static Toolbar getTaskListToolbar() {
        return taskListToolbar;
    }

    /**
     * @return The summary Toolbar object
     */
    public static Toolbar getSummaryToolbar() {
        return summaryToolbar;
    }

    /**
     * Creates a baseline Toolbar object that has all objects/buttons/etc. we
     * always want present in a toolbar
     *
     * @return A baseline Toolbar object
     */
    private Toolbar createBaseToolbar() {
        Toolbar toolbar = new Toolbar();
        current.setToolbar(toolbar);
        addNewTaskButton(toolbar);
        return toolbar;
    }

    /**
     * Creates a baseline Toolbar and adds any taskList specific features
     *
     * @return A Toolbar object for the taskList tab
     */
    private Toolbar createTaskListToolbar() {
        Toolbar toolbar = createBaseToolbar();
        toolbar.setTitle("Tasks");
        toolbar.addSearchCommand(TaskList::addSearch);
        return toolbar;
    }

    /**
     * Creates a baseline Toolbar and adds any summary specific features
     *
     * @return A Toolbar object for the summary tab
     */
    private Toolbar createSummaryToolbar() {
        Toolbar toolbar = createBaseToolbar();
        toolbar.setTitle("Summary");
        return toolbar;
    }

    /**
     * Adds a newTaskButton to the given toolbar
     *
     * @param toolbar The Toolbar we wish to add an addNewTaskButton to
     */
    private void addNewTaskButton(Toolbar toolbar) {
        Button addButton = new Button();
        addButton.addActionListener(e -> new TaskEditor(TaskEditor.TITLE_CREATE));
        setAddNewTaskButtonIcon(addButton);
        toolbar.addComponent(BorderLayout.EAST, addButton);
    }

    /**
     * Adds the new task icon to a button
     *
     * @param button The Button we wish to add the icon to
     */
    private void setAddNewTaskButtonIcon(Button button) {
        try {
            button.setIcon(Image.createImage("/addbutton.png").scaled(80, 80));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
