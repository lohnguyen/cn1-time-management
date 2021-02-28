package org.ecs160.a2.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.UIUtils;

public class Summary extends Container implements AppConstants {

    private static List<Task> taskList;

    private UpdateableContainer page1, page2;

    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setScrollableY(true);

        // title
        this.add(UIUtils.createLabel("Summary", NATIVE_BOLD, 0x000000, 8.0f));

        // Selection
        Container buttonContainer = new Container(new GridLayout(1, 2));
        Button page1Button = new Button ("Everything");
        page1Button.addActionListener((e) -> selectPageButtonAction(e));
        buttonContainer.add(page1Button);
        Button page2Button = new Button ("By Size");
        page2Button.addActionListener((e) -> selectPageButtonAction(e));
        buttonContainer.add(page2Button);
        this.add(buttonContainer);

        // setup the different summary pages
        this.page1 = new SummaryAll();
        this.page1.setHidden(false); // default visible
        this.add(this.page1);
        this.page2 = new SummarySize();
        this.page2.setHidden(true); // default hidden
        this.add(this.page2);

        // call function on refresh (temporary, can have a better solution)
        this.addPullToRefresh(() -> updateContainer());
        this.updateContainer();
        this.page2.updateContainer(taskList);
    }

    // fired when page button is tapped
    private void selectPageButtonAction (ActionEvent e) {
        Button button = (Button) e.getComponent();
        switch (button.getText()) {
            case "Everything":
                this.page2.setHidden(true);
                this.page1.setHidden(false);
                break;
            case "By Size":
                this.page1.setHidden(true);
                this.page2.setHidden(false);
        }
    }

    // read in from the database
    private void reloadTaskList () {
        taskList = (List) Database.readAll(Task.OBJECT_ID);
    }

    // called whenever the labels need updating
    // TODO: onload? on refresh?
    public void updateContainer () {
        this.reloadTaskList(); // refresh the tasks first
        if (taskList.size() > 0) {
            if (!this.page1.isHidden()) { //
                this.page1.updateContainer(taskList);
            } else if (!this.page2.isHidden()) {
                this.page2.updateContainer(taskList);
            }
        }
    }
}