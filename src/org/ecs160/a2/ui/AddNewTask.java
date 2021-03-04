package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import org.ecs160.a2.AppMain;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;

import java.util.ArrayList;
import java.util.List;

public class AddNewTask extends Dialog {

    public AddNewTask() {

        super("New Task", new BorderLayout());
        setDisposeWhenPointerOutOfBounds(true);
        constructView();

        int displayHeight = Display.getInstance().getDisplayHeight();
        show(displayHeight/8, 0, 0, 0);
    }

    private void constructView() {
        TextModeLayout textLayout = new TextModeLayout(3, 2);
        Form newTaskForm = new Form("Enter Task Details", textLayout);

        TextComponent taskName = new TextComponent().label("Task Name");
        TextComponent taskTags = new TextComponent().label("Task Tags");
        TextComponent taskDescription = new TextComponent().label("Description").multiline(true);

        MultiButton sizeButton = new MultiButton("Size");
        sizeButton.addActionListener(e -> showSizePopup(sizeButton));

        newTaskForm.addAll(taskName, sizeButton, taskTags, taskDescription);
        add(BorderLayout.NORTH, newTaskForm);

        Button addTaskButton = new Button("Add Task");
        addTaskButton.addActionListener(e ->
                addTaskToDatabase(taskName.getText(), sizeButton.getText(),
                        taskTags.getText(), taskDescription.getText())
        );

        add(BorderLayout.SOUTH, addTaskButton);
    }

    private void addTaskToDatabase(String name, String size, String tags,
                                   String description) {
        Task newTask = new Task(name, description, size, extractTags(tags));
        Database.write(Task.OBJECT_ID, newTask);
        dispose();
        AppMain.refreshTaskList();
    }

    private List<String> extractTags(String taskTags) {
        List<String> tags = new ArrayList<>();
        String[] splits = taskTags.split(" ");
        for (String split : splits) {
            if (!split.equals("")) tags.add(split);
        }
        return tags;
    }

    private void showSizePopup(MultiButton sizeButton) {
        Dialog sizeDialog = new Dialog();
        sizeDialog.setLayout(BoxLayout.y());
        sizeDialog.getContentPane().setScrollableY(true);

        List<String> taskSizes =  Task.sizes;

        for (int i = 0; i < taskSizes.size(); i++) {
            MultiButton oneSizeButton = new MultiButton(taskSizes.get(i));
            sizeDialog.add(oneSizeButton);
            oneSizeButton.addActionListener(e ->
                    displaySelectedSize(sizeDialog, oneSizeButton, sizeButton)
            );
        }
        sizeDialog.showPopupDialog(sizeButton);
    }

    private void displaySelectedSize(Dialog sizeDialog, MultiButton oneSizeButton, MultiButton sizeButton) {
        sizeButton.setText(oneSizeButton.getText());
        sizeDialog.dispose();
        sizeButton.revalidate();
    }

}