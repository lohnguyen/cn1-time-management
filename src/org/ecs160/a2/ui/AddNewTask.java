package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.TextModeLayout;

public class AddNewTask {

    public void getTaskDialog() {
        Dialog addNewTaskDialog = new Dialog("New Task");
        addNewTaskDialog.setLayout(new BorderLayout());
        int displayHeight = Display.getInstance().getDisplayHeight();
        addNewTaskDialog.setDisposeWhenPointerOutOfBounds(true);
        displayTaskForm(addNewTaskDialog);
        addNewTaskDialog.show(displayHeight/4, 0, 0, 0);
    }

    private void displayTaskForm(Dialog addNewTaskDialog) {
        TextModeLayout textLayout = new TextModeLayout(3,2);
        Form newTaskForm = new Form("Enter Task Details", textLayout);

        TextComponent taskName = new TextComponent().label("Task Name");
        TextComponent taskTags = new TextComponent().label("Task Tags");

        // Sizes start -----------------------
        String[] sizes = {"None", "S", "M", "L", "XL"};
        MultiButton sizeButton = new MultiButton("Size");
        //sizeButton.addActionListener(e->showSizePopup(sizes, sizeButton));
        // Sizes Stop -----------------------

        TextComponent taskDescription = new TextComponent().label("Description").multiline(true);

        newTaskForm.add(textLayout.createConstraint(), taskName);
        newTaskForm.add(sizeButton);
        newTaskForm.add(taskTags);
        newTaskForm.add(taskDescription);


        Button addTaskButton = new Button("Add Task");
        //addTaskButton.addActionListener(e->addTaskIntoDatabase(addNewTaskDialog, taskName.getText(), sizeButton.getText(), taskTags.getText(), taskDescription.getText()));

        addNewTaskDialog.add(BorderLayout.SOUTH, addTaskButton);
        addNewTaskDialog.add(BorderLayout.NORTH, newTaskForm);
    }
}
