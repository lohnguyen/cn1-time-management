package org.ecs160.a2.ui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.models.TimeSpan;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.TimeUtils;
import org.ecs160.a2.utils.UIUtils;

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
        displayTitle();
        displaySize();
        displayDescription();
        displayTags();
        displayTotalTime();
        displayTimeline();
    }

    private void setToolbar() {
        Toolbar tb = new Toolbar();
        setToolbar(tb);
        tb.setTitle("Task Details");
        setBackCommand();
    }

    private void displayTitle() {
        SpanLabel title = UIUtils.createSpanLabel(task.getTitle(),
                NATIVE_REGULAR, COLOR_TITLE, FONT_SIZE_TITLE);
        title.getAllStyles().setAlignment(Component.CENTER);
        add(title);
    }

    private void displaySize() {
        addHeader("Size");
        Label size = new Label(task.getSize());
        add(size);
    }

    private void displayDescription() {
        addHeader("Description");
        String description = task.getDescription();
        if (description.equals(""))
            description = task.getTitle() + " has no descriptions yet!";
        add(new SpanLabel(description));
    }

    private void displayTags() {
        addHeader("Tags");

        if (task.getTags().isEmpty()) {
            add(new SpanLabel(task.getTitle() + " has no tags yet!"));
            return;
        }

        Container container = new Container(new FlowLayout());
        for (String t : task.getTags()) container.add(createTag(t));
        add(container);
    }

    private void displayTimeline() {
        addHeader("Timeline");
        if (task.getTimeSpans().isEmpty()) {
            add(new SpanLabel(task.getTitle() + " has no time intervals yet!"));
            return;
        }
        for (TimeSpan s : task.getTimeSpans()) add(createTimeIntervalStr(s));
    }

    private void displayTotalTime() {
        addHeader("Total Time");
        Label totalTime = UIUtils.createLabel(
                TimeUtils.timeAsString(task.getTotalTime()),
                AppConstants.NATIVE_REGULAR, COLOR_REGULAR, FONT_SIZE_TIME);
        totalTime.getAllStyles().setAlignment(Component.CENTER);
        add(totalTime);
    }

    /**
     * Add header for various fields
     *
     * @param content The text that goes into the header
     */
    private void addHeader(String content) {
        add(UIUtils.createSpanLabel(content, NATIVE_REGULAR, COLOR_TITLE,
                FONT_SIZE_SUB_TITLE));
    }

    /**
     * Set back command for the left chevron arrow in the Toolbar
     */
    private void setBackCommand() {
        Command back = new Command("Back") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                prev.showBack();
            }
        };

        getToolbar().setBackCommand(back);
        Button button = getToolbar().findCommandComponent(back);
        button.setIcon(UIUtils.createBackIcon());
    }

    /**
     * Create a flex CN1 Container to display all tags horizontally
     */
    private Container createTagContainer() {
        Container container = new Container(new FlowLayout());
        Style style = container.getAllStyles();
        style.setBgTransparency(225);
        style.setBorder(RoundBorder.create().rectangle(true).color(0xff884b));
        style.setMargin(Component.RIGHT, 10);
        return container;
    }

    /**
     * Create a round orange tag
     */
    private Container createTag(String t) {
        Container tag = createTagContainer();
        tag.add(UIUtils.createTagIcon());

        Label label = new Label(t);
        label.getAllStyles().setFgColor(0xffffff);
        tag.add(label);

        return tag;
    }

    /**
     * Create a time interval string formatted {start} -> {end}
     *
     * @param span The time span to display
     * @return A CN1 Container for the time SpanLabels
     */
    private Container createTimeIntervalStr(TimeSpan span) {
        SpanLabel start = UIUtils.createTimeLabel(span.getStart());
        SpanLabel end = UIUtils.createTimeLabel(span.getEnd());
        Label arrow = new Label("", UIUtils.createNextIcon());
        return FlowLayout.encloseCenter(start, arrow, end);
    }

}