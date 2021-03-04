package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;

import java.util.ArrayList;
import java.util.List;

public class TaskEditor extends Dialog {

    static public final String TITLE_CREATE = "New Task";
    static public final String TITLE_EDIT = "Edit Task";

    private Task task;
    private final String title;

    private TextComponent taskTitle;
    private TextComponent taskTags;
    private TextComponent taskDescription;
    private MultiButton taskSize;

    public TaskEditor(String title) {
        super(title, new BorderLayout());
        this.title = title;
        init();
    }

    public TaskEditor(Task task, String title) {
        super(title, new BorderLayout());
        this.task = task;
        this.title = title;
        init();
    }

    public void init() {
        constructView();
        setDisposeWhenPointerOutOfBounds(true);
        int displayHeight = Display.getInstance().getDisplayHeight();
        show(displayHeight/8, 0, 0, 0);
    }

    private void constructView() {
        TextModeLayout textLayout = new TextModeLayout(3, 2);
        Form form = new Form("Enter Task Details", textLayout);

        taskTitle = new TextComponent().label("Title");
        taskTags = new TextComponent().label("Tags");
        taskDescription = new TextComponent().label("Description").multiline(true);
        taskSize = new MultiButton("Size");
        taskSize.addActionListener(e -> showSizePopup(taskSize));

        if (task != null) fillOutFields();

        form.addAll(taskTitle, taskSize, taskTags, taskDescription);
        add(BorderLayout.NORTH, form);

        Button addButton = new Button(title);
        addButton.addActionListener(e ->  {
            if (task == null) addTaskToDatabase();
            else editTaskInDatabase();
        });
        add(BorderLayout.SOUTH, addButton);
    }

    private void fillOutFields() {
        taskTitle.text(task.getTitle());
        taskTags.text(String.join(" ", task.getTags()));
        taskDescription.text(task.getDescription());
        taskSize.setTextLine1(task.getSize());
    }

    private void addTaskToDatabase() {
        Task newTask = new Task(taskTitle.getText(), taskDescription.getText(),
                taskSize.getText(), extractTags());
        Database.write(Task.OBJECT_ID, newTask);
        dispose();
        TaskList.refresh();
    }

    private void editTaskInDatabase() {
        task.setTitle(taskTitle.getText());
        task.setDescription(taskDescription.getText());
        task.setSize(taskSize.getText());
        task.setTags(extractTags());
        Database.update(Task.OBJECT_ID, task);
        dispose();
        TaskList.refresh();
    }

    private List<String> extractTags() {
        List<String> tags = new ArrayList<>();
        String[] splits = taskTags.getText().split(" ");
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