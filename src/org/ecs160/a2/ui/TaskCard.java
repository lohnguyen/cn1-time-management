package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.TimeUtils;
import org.ecs160.a2.utils.UIUtils;

public class TaskCard extends Container implements AppConstants {

    private final Task task;
    private final Style btnStyle;

    public TaskCard(Task task) {
        super(BoxLayout.y());
        this.task = task;
        this.btnStyle = UIUtils.getCardIconStyle(0x000000);
        constructView();
    }

    private void constructView() {
        MultiButton multiBtn = new MultiButton(task.getTitle());
        if (task.isInProgress()) multiBtn.setTextLine2("In Progress");
        else multiBtn.setTextLine2(TimeUtils.timeAsString(task.getTotalTime()));
        multiBtn.addActionListener(e -> goToDetail(task));

        Container rightBtns = createRightButtons();
        Button archiveBtn = createArchiveButton();
        add(new SwipeableContainer(archiveBtn, rightBtns, multiBtn));
    }

    private Container createRightButtons() {
        Container buttons = new Container(new FlowLayout());

        Button start = createControlButton();
        Button edit = createButton(FontImage.MATERIAL_EDIT, this::onEdit);
        Button delete = createButton(FontImage.MATERIAL_REMOVE_CIRCLE, this::onDelete);

        buttons.addAll(start, edit, delete);

        return buttons;
    }

    private Button createArchiveButton() {
        char i = task.isArchived() ? FontImage.MATERIAL_UNARCHIVE :
                FontImage.MATERIAL_ARCHIVE;
        return createButton(i, this::onArchive);
    }

    private Button createControlButton() {
        char i = !task.isInProgress() ? FontImage.MATERIAL_PLAY_CIRCLE_OUTLINE
                : FontImage.MATERIAL_PAUSE_CIRCLE_OUTLINE;
        return createButton(i, this::onControl);
    }

    private Button createButton(char icon, Runnable listener) {
        Style s = icon != FontImage.MATERIAL_REMOVE_CIRCLE ? btnStyle :
                UIUtils.getCardIconStyle(0xF44336);
        Button button = new Button(FontImage.createMaterial(icon, s));
        button.addActionListener(e -> listener.run());
        return button;
    }

    private void goToDetail(Task task) {
        TaskDetail detail = new TaskDetail(task);
        detail.show();
    }

    private void onControl() {
        if (task.isInProgress()) {
            task.stop();
        } else {
            task.start();
            task.setArchived(false);
        }
        Database.update(Task.OBJECT_ID, task);
        TaskList.refresh();
    }

    private void onEdit() {
        new TaskEditor(task, TaskEditor.TITLE_EDIT);
    }

    private void onDelete() {
        Command delete = new Command("Delete");
        Command cancel = new Command("Cancel");
        Command[] commands = new Command[]{delete, cancel};
        Command choice = Dialog.show("Delete this task",
                "Are you sure you want to delete " + task.getTitle() + "?",
                commands);

        if (choice == cancel) return;
        Database.delete(Task.OBJECT_ID, task.getID());
        TaskList.refresh();
    }

    /**
     * Sets the task's archive field and updates database
     */
    private void onArchive() {
        if (task.isArchived()) {
            task.setArchived(false);
        } else {
            task.stop();
            task.setArchived(true);
        }

        Database.update(Task.OBJECT_ID, task);
        TaskList.refresh();
    }

}