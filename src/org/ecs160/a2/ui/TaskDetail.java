package org.ecs160.a2.ui;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.models.TimeSpan;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TaskDetail extends Form implements AppConstants {

    private final Form prev;
    private final Task task;

    TaskDetail(Task t) {
        super(BoxLayout.y());

        prev = Display.getInstance().getCurrent();
        task = t;

        constructView();
    }

    private void constructView() {
        setToolbar();
        addTitle();
        addSize();
        addDescription();
        addTags();
        addTotalTime();
        addTimeline();
    }

    private void addTitle() {
        Label title = new Label(task.getTitle());
        Style style = title.getAllStyles();
        style.setFont(UIUtils.getTitleFont());
        style.setFgColor(COLOR_TITLE);
        add(title);
    }

    private void addSize() {
        addHeader("Size");
        Label size = new Label(task.getSize());
        add(size);
    }

    private void addHeader(String content) {
        add(UIUtils.createLabel(content, NATIVE_REGULAR, COLOR_TITLE,
                FONT_SIZE_SUB_TITLE));
    }

    private void addDescription() {
        addHeader("Description");
        String description = task.getDescription();
        if (description.equals(""))
            description = task.getTitle() + " has no descriptions yet!.";
        add(new Label(description));
    }

    private FontImage getTagIcon() {
        Style s = new Style();
        s.setBgColor(0xff884b);
        s.setFgColor(0xffffff);
        return FontImage.createMaterial(FontImage.MATERIAL_LOCAL_OFFER, s, 3);
    }

    private Container getTagContainer() {
        Container container = new Container(new FlowLayout());
        Style style = container.getAllStyles();
        style.setBgTransparency(225);
        style.setBorder(RoundBorder.create().rectangle(true).color(0xff884b));
        style.setMargin(Component.RIGHT, 10);
        return container;
    }

    private Container getTag(String t) {
        Container tag = getTagContainer();
        tag.add(getTagIcon());

        Label label = new Label(t);
        label.getAllStyles().setFgColor(0xffffff);
        tag.add(label);

        return tag;
    }

    private void addTags() {
        addHeader("Tags");

        if (task.getTags().size() == 0) {
            add(new Label(task.getTitle() + " has no tags yet!"));
            return;
        }

        Container container = new Container(new FlowLayout());
        for (String t : task.getTags()) container.add(getTag(t));
        add(container);
    }

    private Container getSpanStr(TimeSpan span) {
        Label start = new Label(TimeSpan.getTimeStr(span.getStart()));
        Label end = new Label(TimeSpan.getTimeStr(span.getEnd()));
        Label arrow = new Label("", UIUtils.getNextIcon());
        return FlowLayout.encloseCenter(start, arrow, end);
    }

    private void addTimeline() {
        addHeader("Timeline");
        for (TimeSpan s : task.getTimeSpans()) add(getSpanStr(s));
    }

    private void addTotalTime() {
        addHeader("Total Time");
        Label totalTime = new Label(task.getTotalTimeStr());
        Style style = totalTime.getAllStyles();
        style.setFont(UIUtils.getTitleFont());
        style.setAlignment(Component.CENTER);
        add(totalTime);
    }

    private void setToolbar() {
        Toolbar tb = new Toolbar();
        setToolbar(tb);
        tb.setTitle("Task Details");
        setBackCommand();
    }

    private void setBackCommand() {
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                prev.showBack();
            }
        };

        getToolbar().setBackCommand(back);
        Button button = getToolbar().findCommandComponent(back);
        button.setIcon(UIUtils.getBackIcon());
    }

}