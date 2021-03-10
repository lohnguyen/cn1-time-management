package org.ecs160.a2.ui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.models.TimeSpan;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.TimeUtils;
import org.ecs160.a2.utils.UIUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskEditor extends Dialog {

    static public final String TITLE_CREATE = "New Task";
    static public final String TITLE_EDIT = "Edit Task";

    private Task task;
    private List<TimeSpan> timeSpans;

    private TextComponent taskTitle;
    private TextComponent taskTags;
    private TextComponent taskDescription;
    private MultiButton taskSize;

    public TaskEditor(String title) {
        super(title, new BorderLayout());
        init();
    }

    public TaskEditor(Task task, String title) {
        super(title, new BorderLayout());
        this.task = task;
        this.timeSpans = task.getTimeSpans();
        init();
    }

    /**
     * Initialize view with all the UI. This is also called when a time span is
     * removed to refresh the view.
     */
    public void init() {
        constructView();
        setDisposeWhenPointerOutOfBounds(true);
        int displayHeight = Display.getInstance().getDisplayHeight();
        show(displayHeight/8, 0, 0, 0);
    }

    /**
     * Construct the editor's view depending on the purpose:
     *   - With DetailForm for creating a task
     *   - With DetailForm and TimeSpanForm for editing a task
     */
    private void constructView() {
        setDetailForm();
        if (isEditForm()) setTimeSpanForm();

        Button addButton = new Button(isEditForm() ? "Save" : "Create");
        addButton.addActionListener(e ->  {
            if (task == null) addTaskToDatabase();
            else editTaskInDatabase();
        });
        add(BorderLayout.SOUTH, addButton);
    }

    /**
     * Attach a CN1 form for all task details (e.g. title, tags, description,
     * size) and prefill all data if the editor is for editing
     */
    private void setDetailForm() {
        Form form = createForm("Task Details");

        taskTitle = new TextComponent().label("Title");
        taskTags = new TextComponent().label("Tags");
        taskDescription = new TextComponent().label("Description").multiline(true);
        taskSize = new MultiButton("Size");
        taskSize.addActionListener(e -> showSizePopup(taskSize));

        if (isEditForm()) fillOutFields();
        form.addAll(taskTitle, taskSize, taskTags, taskDescription);
        add(BorderLayout.NORTH, form);
    }

    /**
     * Create a CN1 picker for time span editing
     *
     * @param ldt The initial time to set the picker
     * @return A new CN1 Picker instance
     */
    private Picker createDateTimePicker(LocalDateTime ldt) {
        Picker picker = new Picker();
        picker.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        picker.setDate(TimeUtils.toDate(ldt));
        return picker;
    }

    /**
     * Attach a CN1 form for editing the task's all time intervals
     */
    private void setTimeSpanForm() {
        Form form = createForm("Time Intervals");

        for (TimeSpan span : timeSpans) {
            Label arrow = new Label("", UIUtils.getNextIcon());
            Picker start = createDateTimePicker(span.getStart());
            start.addActionListener(e -> span.setStart(start.getDate()));

            if (span.isRunning()) {
                SpanLabel end = new SpanLabel(TimeSpan.getTimeStr(span.getEnd()));
                form.add(FlowLayout.encloseCenter(start, arrow, end));
            } else {
                Picker end = createDateTimePicker(span.getEnd());
                end.addActionListener(e -> span.setEnd(end.getDate()));
                form.add(FlowLayout.encloseCenter(start, arrow, end));
            }

            Button delete = createDeleteButton();
            delete.addActionListener(e -> onDeleteButtonClicked(span));
            form.add(BorderLayout.EAST, delete);
        }

        add(BorderLayout.CENTER, form);
    }

    /**
     * Create a delete button for time intervals' removal
     * @return A CN1 Button instance
     */
    private Button createDeleteButton() {
        Button button = new Button("Delete");
        Style s = button.getAllStyles();
        s.setBgColor(0xd62d20);
        s.setBgTransparency(225);
        s.setFgColor(0xffffff);
        s.setMarginBottom(50);
        return button;
    }

    /**
     * Listener of delete click to show confirm dialog
     *
     * @param span The TimeSpan instance to remove
     */
    private void onDeleteButtonClicked(TimeSpan span) {
        Command delete = new Command("Delete");
        Command cancel = new Command("Cancel");
        Command[] commands = new Command[]{delete, cancel};
        Command choice = Dialog.show("Delete this time interval",
                "Are you sure you want to proceed? This action cannot be " +
                        "reverted.",
                commands);

        if (choice == cancel) return;

        timeSpans.remove(span);
        task.setTimeSpans(timeSpans);
        Database.update(Task.OBJECT_ID, task);
        TaskList.refresh();
        init();
    }

    /**
     * Repopulate task entry dialog for further editing
     */
    private void fillOutFields() {
        taskTitle.text(task.getTitle());
        taskTags.text(String.join(" ", task.getTags()));
        taskDescription.text(task.getDescription());
        taskSize.setTextLine1(task.getSize());
    }

    /**
     * Extract task details entered and write into database
     */
    private void addTaskToDatabase() {
        Task newTask = new Task(taskTitle.getText(), taskDescription.getText(),
                createSizeText(), extractTags());
        Database.write(Task.OBJECT_ID, newTask);
        dispose();
        TaskList.refresh();
    }

    /**
     * Resets task details to update in database
     */
    private void editTaskInDatabase() {
        task.setTitle(taskTitle.getText())
            .setDescription(taskDescription.getText())
            .setSize(createSizeText())
            .setTags(extractTags())
            .setTimeSpans(timeSpans);

        Database.update(Task.OBJECT_ID, task);
        dispose();
        TaskList.refresh();
    }

    /**
     * Gets size text selected from dialog window
     *
     * @return Returns selected size. If not selected, returns "None"
     */
    private String createSizeText() {
        return taskSize.getText().equals("Size") ? "None" : taskSize.getText();
    }

    /**
     * Splits the tags TextField into multiple tags
     */
    private List<String> extractTags() {
        List<String> tags = new ArrayList<>();
        String[] splits = taskTags.getText().split(" ");
        for (String split : splits) {
            if (!split.equals("")) tags.add(split);
        }
        return tags;
    }

    /**
     * Searches a list of tasks for specific tag or title
     *
     * @param sizeButton Multibutton holder for all single buttons (one size buttons)
     */
    private void showSizePopup(MultiButton sizeButton) {
        Dialog sizeDialog = new Dialog();
        sizeDialog.setLayout(BoxLayout.y());
        sizeDialog.getContentPane().setScrollableY(true);

        List<String> taskSizes = Task.sizes;

        for (String size : taskSizes) {
            MultiButton oneSizeButton = new MultiButton(size);
            sizeDialog.add(oneSizeButton);
            oneSizeButton.addActionListener(e ->
                    displaySelectedSize(sizeDialog, oneSizeButton, sizeButton)
            );
        }
        sizeDialog.showPopupDialog(sizeButton);
    }

    /**
     * Searches a list of tasks for specific tag or title
     *
     * @param sizeDialog The dialog to be populated with sizes
     * @param oneSizeButton Single button with a specific size
     * @param sizeButton Multibutton holder for all single buttons (one size buttons)
     */
    private void displaySelectedSize(Dialog sizeDialog, MultiButton oneSizeButton, MultiButton sizeButton) {
        sizeButton.setText(oneSizeButton.getText());
        sizeDialog.dispose();
        sizeButton.revalidate();
    }

    /**
     * Check if the editor is for editing or creating.
     *
     * @return whether the editor is for editing or not
     */
    private boolean isEditForm() {
        return task != null;
    }

    /**
     * Create a CN1 form with a title and specific layout
     *
     * @param title The title for the form
     * @return A new CN1 Form instance
     */
    private Form createForm(String title) {
        TextModeLayout textLayout = new TextModeLayout(3, 2);
        return new Form(title, textLayout);
    }

}