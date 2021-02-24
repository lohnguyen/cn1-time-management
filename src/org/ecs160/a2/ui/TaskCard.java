package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.DurationUtils;
import org.ecs160.a2.utils.AppConstants;

public class TaskCard extends Container implements AppConstants {

    Task task;

    public TaskCard(Task task) {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.task = task;

        MultiButton card = new MultiButton(task.getTitle());
        card.setTextLine2(DurationUtils.durationStr(task.getTotalTime()));

        card.addActionListener(e -> {
            goToDetail(task);
        });

        Container content = new SwipeableContainer(null, getButtons(), card);
        this.add(content);
    }

    private Container getButtons() {
        Container buttons = new Container(new FlowLayout());
        Font fnt = NATIVE_LIGHT.derive(Display.getInstance()
                .convertToPixels(5, true), Font.STYLE_PLAIN);

        Style s = new Style(0, 0, fnt, (byte) 0);
        Style s2 = new Style(0xF44336, 0, fnt, (byte) 0);

        buttons.add(createPlayButton(s));

        Button editButton = createButton(FontImage.MATERIAL_SETTINGS, s);
        buttons.add(editButton);

        Button deleteButton = createButton(FontImage.MATERIAL_REMOVE_CIRCLE, s2);
        deleteButton.addActionListener(e -> {
            Database.delete(Task.OBJECT_ID, task.getTitle());
        });
        buttons.add(deleteButton);

        return buttons;
    }

    private Button createPlayButton(Style style) {
        if (!task.isInProgress())
            return createButton(FontImage.MATERIAL_PLAY_CIRCLE_OUTLINE, style);
        return createButton(FontImage.MATERIAL_PAUSE_CIRCLE_OUTLINE, style);
    }

    private Button createButton(char icon, Style style) {
        return new Button(FontImage.createMaterial(icon, style));
    }

    private void goToDetail(Task task) {
        TaskDetail detail = new TaskDetail(task);
        detail.show();
    }

}