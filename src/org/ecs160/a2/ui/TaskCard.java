package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.AppConstants;

import java.util.function.Consumer;


public class TaskCard extends Container implements AppConstants {

    static private final Font fnt = NATIVE_LIGHT.derive(Display.getInstance()
            .convertToPixels(5, true), Font.STYLE_PLAIN);
    static private final Style style = new Style(0, 0, fnt, (byte) 0);
    static private final Style styleWarn = new Style(0xF44336, 0, fnt, (byte) 0);

    private final Task task;

    public Consumer<Task> onStarted;
    public Consumer<Task> onStopped;
    public Consumer<Task> onDeleted;

    /**
     * @param task      the task
     * @param onStarted Called after the task is started to inform TaskList
     *                  of moving this task into active
     * @param onStopped Called after the task is stopped to inform TaskList
     *                  of moving this task into inactive
     * @param onDeleted Called after the task is removed from the database to
     *                  inform TaskList to remove this entry
     */
    public TaskCard(Task task, Consumer<Task> onStarted,
                    Consumer<Task> onStopped, Consumer<Task> onDeleted) {
        super(BoxLayout.y());
        this.task = task;
        this.onStarted = onStarted;
        this.onStopped = onStopped;
        this.onDeleted = onDeleted;

        MultiButton multiButton = new MultiButton(task.getTitle());
        if (task.isInProgress()) multiButton.setTextLine2("In Progress");
        else multiButton.setTextLine2(task.getTotalTimeStr());
        multiButton.addActionListener(e -> goToDetail(task));

        Container buttons = new Container(new FlowLayout());

        Button startButton = createControlButton(style);
        Button editButton = createButton(FontImage.MATERIAL_EDIT, style,
                this::onEditButtonClicked);
        Button deleteButton = createButton(FontImage.MATERIAL_REMOVE_CIRCLE,
                styleWarn, this::onDeleteButtonClicked);
        buttons.addAll(startButton, editButton, deleteButton);

        Button archiveButton = createArchiveButton(style);

        SwipeableContainer swipeContainer = new SwipeableContainer(archiveButton, buttons,
                multiButton);

        add(swipeContainer);
    }

    private Button createArchiveButton(Style style) {
        if (task.isArchived()) {
            return createButton(FontImage.MATERIAL_UNARCHIVE, style,
                    this::onArchiveButtonClicked);
        }
        return createButton(FontImage.MATERIAL_ARCHIVE, style,
                this::onArchiveButtonClicked);
    }

    public TaskCard(Task task) {
        this(task, null, null, null);
    }


    private Button createControlButton(Style style) {
        if (!task.isInProgress())
            return createButton(FontImage.MATERIAL_PLAY_CIRCLE_OUTLINE, style,
                    this::onStartButtonClicked);
        return createButton(FontImage.MATERIAL_PAUSE_CIRCLE_OUTLINE, style,
                this::onStartButtonClicked);
    }

    private Button createButton(char icon, Style style, Runnable listener) {
        Button button = new Button(FontImage.createMaterial(icon, style));
        button.addActionListener((ev) -> listener.run());
        return button;
    }

    private void goToDetail(Task task) {
        TaskDetail detail = new TaskDetail(task);
        detail.show();
    }

    private void onStartButtonClicked() {
        if (!task.isInProgress()) {
            task.start();
            task.setArchived(false);
            if (onStarted != null) onStarted.accept(task);
        } else {
            task.stop();
            if (onStopped != null) onStopped.accept(task);
        }
        Database.update(Task.OBJECT_ID, task);
        TaskList.refresh();
    }

    private void onEditButtonClicked() {
        new TaskEditor(task, TaskEditor.TITLE_EDIT);
    }

    private void onDeleteButtonClicked() {
        Command delete = new Command("Delete");
        Command cancel = new Command("Cancel");
        Command[] commands = new Command[]{delete, cancel};
        Command choice = Dialog.show("Delete this task",
                "Are you sure you want to delete " + task.getTitle() + "?",
                commands);

        if (choice == cancel) return;
        if (this.onDeleted != null) onDeleted.accept(task);
        Database.delete(Task.OBJECT_ID, task.getID());
        TaskList.refresh();
    }

    private void onArchiveButtonClicked() {
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