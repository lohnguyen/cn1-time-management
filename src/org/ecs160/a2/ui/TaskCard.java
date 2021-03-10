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
        this.btnStyle = UIUtils.createCardIconStyle(0x000000);
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

    /**
     * Create all the right buttons for the card's Swipable
     *
     * @return A CN1 Container of all buttons for the right of Swipeable
     */
    private Container createRightButtons() {
        Container buttons = new Container(new FlowLayout());

        Button start = createControlButton();
        Button edit = createButton(FontImage.MATERIAL_EDIT, this::onEdit);
        Button delete = createButton(FontImage.MATERIAL_REMOVE_CIRCLE,
                this::onDelete);

        buttons.addAll(start, edit, delete);

        return buttons;
    }

    /**
     * Create the control button as archive or unarchive
     *
     * @return A CN1 Button for the left of Swipeable
     */
    private Button createArchiveButton() {
        char i = task.isArchived() ? FontImage.MATERIAL_UNARCHIVE :
                FontImage.MATERIAL_ARCHIVE;
        return createButton(i, this::onArchive);
    }

    /**
     * Create the control button as start or stop
     *
     * @return A CN1 Button for start or stop
     */
    private Button createControlButton() {
        char i = !task.isInProgress() ? FontImage.MATERIAL_PLAY_CIRCLE_OUTLINE
                : FontImage.MATERIAL_PAUSE_CIRCLE_OUTLINE;
        return createButton(i, this::onControl);
    }

    /**
     * Create a button for CN1 Swipeable
     *
     * @param icon The icon for the button
     * @param listener The callback for the button's click
     * @return A CN1 Button
     */
    private Button createButton(char icon, Runnable listener) {
        Style s = icon != FontImage.MATERIAL_REMOVE_CIRCLE ? btnStyle :
                UIUtils.createCardIconStyle(0xF44336);
        Button button = new Button(FontImage.createMaterial(icon, s));
        button.addActionListener(e -> listener.run());
        return button;
    }

    /**
     * Show all task's details in a new form when user clicks on the card
     */
    private void goToDetail(Task task) {
        TaskDetail detail = new TaskDetail(task);
        detail.show();
    }

    /**
     * Start or stop the task, update db, and refresh
     */
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

    /**
     * Open up TaskEditor for task editing
     */
    private void onEdit() {
        new TaskEditor(task, TaskEditor.TITLE_EDIT);
    }

    /**
     * Show confirm dialog before deleting task in db and refresh
     */
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
     * Set the task's archive field and updates database
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