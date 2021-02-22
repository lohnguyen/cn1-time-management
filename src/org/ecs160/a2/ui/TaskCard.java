package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.DurationUtils;

public class TaskCard extends Container {

    Task task;


    public TaskCard(Task task) {
        this.task = task;


        MultiButton card = new MultiButton(task.getTitle());
        card.setTextLine2(DurationUtils.durationStr(task.getTotalTime()));


        Container buttons = new Container(new FlowLayout());

        Font fnt = Font.createTrueTypeFont("native:MainLight", "native" +
                ":MainLight").
                derive(Display.getInstance().convertToPixels(5, true),
                        Font.STYLE_PLAIN);
        Style s = new Style(0, 0, fnt, (byte) 0);
        Style s2 = new Style(0xF44336, 0, fnt, (byte) 0);

        if (!task.isInProgress()) {
            Button startButton = new Button(
                    FontImage.createMaterial(
                            FontImage.MATERIAL_PLAY_CIRCLE_OUTLINE,
                            s
                    )
            );
            buttons.add(startButton);
        } else {
            Button stopButton = new Button(
                    FontImage.createMaterial(
                            FontImage.MATERIAL_PAUSE_CIRCLE_OUTLINE,
                            s
                    )
            );
            buttons.add(stopButton);
        }


        Button editButton = new Button(
                FontImage.createMaterial(
                        FontImage.MATERIAL_SETTINGS,
                        s
                )
        );
        buttons.add(editButton);

        Button deleteButton = new Button(
                FontImage.createMaterial(
                        FontImage.MATERIAL_REMOVE_CIRCLE,
                        s2
                )
        );
        buttons.add(deleteButton);
        final Container content = new SwipeableContainer(null, buttons, card);

        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        this.add(content);
    }
}
