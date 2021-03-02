package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.UITimer;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.DurationUtils;
import org.ecs160.a2.utils.AppConstants;

import java.util.function.Consumer;

import java.util.function.Consumer;


public class TaskCard extends Container implements AppConstants {

    Task task;

    private Button startButton;
    private MultiButton multiButton;
    private UITimer timer;
    private SwipeableContainer swipeContainer;

    public Consumer<Task> onStarted;
    public Consumer<Task> onStopped;
    public Consumer<Task> onDeleted;


    static private Font fnt = NATIVE_LIGHT.derive(Display.getInstance()
            .convertToPixels(5, true), Font.STYLE_PLAIN);
    static private Style style = new Style(0, 0, fnt, (byte) 0);
    static private Style styleWarn = new Style(0xF44336, 0, fnt, (byte) 0);

    /**
     * @param task      the task
     * @param onStarted Called after the task is started to inform TaskList
     *                  of moving this task into active
     * @param onStopped Called after the task is stopped to inform TaskList
     *                  of moving this task into inactive
     * @param onDeleted Called after the task is removed from the database to
     *                  inform TaskList to remove this entry
     */
    public TaskCard(
            Task task,
            Consumer<Task> onStarted,
            Consumer<Task> onStopped,
            Consumer<Task> onDeleted
    ) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.task = task;
        this.onStarted = onStarted;
        this.onStopped = onStopped;
        this.onDeleted = onDeleted;

        this.multiButton = new MultiButton(task.getTitle());
        multiButton.setTextLine2(DurationUtils.durationStr(task.getTotalTime()));

        multiButton.addActionListener(e ->
                goToDetail(task)
        );

        Container buttons = new Container(new FlowLayout());

        this.startButton = createControlButton(style);
        buttons.add(startButton);

        Button editButton = createButton(FontImage.MATERIAL_SETTINGS, style,
                this::onEditButtonClicked);
        buttons.add(editButton);

        Button deleteButton = createButton(FontImage.MATERIAL_REMOVE_CIRCLE,
                styleWarn, this::onDeleteButtonClicked);


        buttons.add(deleteButton);

        this.swipeContainer = new SwipeableContainer(null, buttons,
                multiButton);

        this.add(swipeContainer);
    }

    public TaskCard(Task task) {
        this(task, null, null, null);
    }


    private Button createControlButton(Style style) {
        if (!task.isInProgress())
            return createButton(FontImage.MATERIAL_PLAY_CIRCLE_OUTLINE, style
                    , this::onStartButtonClicked);
        return createButton(FontImage.MATERIAL_PAUSE_CIRCLE_OUTLINE, style,
                this::onStartButtonClicked);
    }

    private Button createButton(char icon, Style style,
                                Runnable listener) {
        final Button button = new Button(FontImage.createMaterial(icon, style));
        button.addActionListener((ev) -> listener.run());
        return button;
    }

    private void goToDetail(Task task) {
        TaskDetail detail = new TaskDetail(task);
        detail.show();
    }

    private void updateState() {
        multiButton.setTextLine2(DurationUtils.durationStr(task.getTotalTime()));
        if (!task.isInProgress()) {
            startButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_PLAY_CIRCLE_OUTLINE, style));
        } else {
            startButton.setIcon(FontImage.createMaterial(FontImage.MATERIAL_PAUSE_CIRCLE_OUTLINE, style));
        }
        System.out.println("updated");
    }

    private void onStartButtonClicked() {
        if (this.timer != null) {
            this.timer.cancel();
        }
        if (!task.isInProgress()) {
            task.start();
            this.timer = UITimer.timer(1000, true, this::updateState);
            if (this.onStarted != null) {
                this.onStarted.accept(this.task);
            }
        } else {
            task.stop();
            if (this.onStopped != null) {
                this.onStopped.accept(this.task);
            }
        }
        this.updateState();
    }

    private void onEditButtonClicked() {

    }

    private void onDeleteButtonClicked() {
        Database.delete(Task.OBJECT_ID, task.getTitle());
        if (this.onDeleted != null) {
            this.onDeleted.accept(this.task);
        }
    }

}