package org.ecs160.a2.ui;

import java.util.List;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

import org.ecs160.a2.models.Task;
import org.ecs160.a2.ui.containers.UpdateableContainer;
import org.ecs160.a2.utils.AppConstants;
import org.ecs160.a2.utils.Database;
import org.ecs160.a2.utils.UIUtils;

/**
 * The container that houses the Summary containers for all Tasks
 */
public class Summary extends UpdateableContainer implements AppConstants {

    // the current state of TaskList for this page
    private static List<Task> taskList;

    private UpdateableContainer page1, page2;

    /**
     * Default constructor that assembles the children of this container
     */
    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setScrollableY(true); // can scroll vertically

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

        // Setup pull to refresh for this container
        this.addPullToRefresh(() -> updateSubContainers());
        this.updateSubContainers();
        this.page2.updateContainer(taskList);
    }

    // action listener that allows for the selection of a page
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

    // reload the internal task list by reading in from the static Database
    private void reloadTaskList () {
        taskList = (List) Database.readAll(Task.OBJECT_ID);
    }

    /**
     * Update the visible subpages of this Container
     */
    public void updateSubContainers () {
        this.reloadTaskList(); // refresh the tasks first
        if (taskList.size() > 0) {
            if (!this.page1.isHidden()) { // which page is visible
                this.page1.updateContainer(taskList);
            } else if (!this.page2.isHidden()) {
                this.page2.updateContainer(taskList);
            }
        }
    }


    /**
     * Update the source child with the internal Task List
     */
    @Override
    protected void childAsksForUpdate (UpdateableContainer source) {
        source.updateContainer(taskList);
    }

    /**
     * Update the sub containers when this containers need updating
     */
    @Override
    public void updateContainer(List<Task> taskList) {
        this.updateSubContainers();
    }
}