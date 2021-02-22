package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import static com.codename1.ui.CN.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import org.ecs160.a2.models.Task;
import com.codename1.io.Log;
import org.ecs160.a2.utils.Database;

import java.util.ArrayList;
import java.util.List;

public class AddNewTask {

    public void getTaskDialog() {
        Dialog addNewTaskDialog = new Dialog("New Task");
        addNewTaskDialog.setLayout(new BorderLayout());
        int displayHeight = Display.getInstance().getDisplayHeight();
        addNewTaskDialog.setDisposeWhenPointerOutOfBounds(true);
        displayTaskForm(addNewTaskDialog);
        addNewTaskDialog.show(displayHeight/8, 0, 0, 0);
    }

    private void displayTaskForm(Dialog addNewTaskDialog) {
        TextModeLayout textLayout = new TextModeLayout(3,2);
        Form newTaskForm = new Form("Enter Task Details", textLayout);

        TextComponent taskName = new TextComponent().label("Task Name");
        TextComponent taskTags = new TextComponent().label("Task Tags");
        TextComponent taskDescription = new TextComponent().label("Description").multiline(true);

        MultiButton sizeButton = new MultiButton("Size");
        sizeButton.addActionListener(e->showSizePopup(sizeButton));

        newTaskForm.addAll(taskName, sizeButton, taskTags, taskDescription);

        Button addTaskButton = new Button("Add Task");
        addTaskButton.addActionListener(e->addTaskIntoDatabase(addNewTaskDialog, taskName.getText(), sizeButton.getText(), taskTags.getText(), taskDescription.getText()));

        addNewTaskDialog.add(BorderLayout.SOUTH, addTaskButton);
        addNewTaskDialog.add(BorderLayout.NORTH, newTaskForm);
    }

    private void addTaskIntoDatabase(Dialog addNewTaskDialog, String taskName, String taskSize, String taskTags, String taskDescription) {

        java.util.List<String> taskTagsList = extractTagStrings(taskTags);
        Task newTask = new Task(taskName, taskDescription);
        newTask.setSize(taskSize);
        newTask.setTags(taskTagsList);

        String key = Task.OBJECT_ID;

        Database.write(key, newTask);
        addNewTaskDialog.dispose();

    }

    private List<String> extractTagStrings(String taskTags) {
        java.util.List<String> separatedTaskTags = new ArrayList<>();
        String[] separatedTaskTagsSplit = taskTags.split(" ");
        for (String tag : separatedTaskTagsSplit) {
            separatedTaskTags.add(tag);
        }
        return separatedTaskTags;
    }

    private void showSizePopup(MultiButton sizeButton) {
        Dialog sizeDialog = new Dialog();
        sizeDialog.setLayout(BoxLayout.y());
        sizeDialog.getContentPane().setScrollableY(true);

        java.util.List<String> taskSizes =  Task.sizes;

        for (int i = 0; i < taskSizes.size(); i++) {
            MultiButton oneSizeButton = new MultiButton(taskSizes.get(i));
            sizeDialog.add(oneSizeButton);
            oneSizeButton.addActionListener(e->displaySelectedSize(sizeDialog, oneSizeButton, sizeButton));
        }
        sizeDialog.showPopupDialog(sizeButton);
    }

    private void displaySelectedSize(Dialog sizeDialog, MultiButton oneSizeButton, MultiButton sizeButton) {
        sizeButton.setText(oneSizeButton.getText());
        sizeDialog.dispose();
        sizeButton.revalidate();
    }
}
