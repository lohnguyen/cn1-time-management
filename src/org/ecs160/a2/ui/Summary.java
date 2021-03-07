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

    private UpdateableContainer page1, page2, page3;

    // method to construct page buttons
    private void addPageButton (Container buttonContainer, String text) {
        Button pageButton = new Button (text);
        pageButton.addActionListener((e) -> selectPageButtonAction(e));
        buttonContainer.add(pageButton);
    }

    /**
     * Default constructor that assembles the children of this container
     */
    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setScrollableY(true); // can scroll vertically

        // title
        this.add(UIUtils.createLabel("Summary", NATIVE_BOLD, COLOR_TITLE,
                                     FONT_SIZE_TITLE));

        // Selection
        Container buttonContainer = new Container(new GridLayout(1, 3));
        addPageButton(buttonContainer, "Everything");
        addPageButton(buttonContainer, "By Size");
        addPageButton(buttonContainer, "By Tags");
        this.add(buttonContainer);

        // setup the different summary pages
        this.page1 = new SummaryAll();
        this.page1.setHidden(false); // default visible
        this.add(this.page1);
        this.page2 = new SummarySize();
        this.page2.setHidden(true); // default hidden
        this.add(this.page2);
        this.page3 = new SummaryTags();
        this.page3.setHidden(true); // default hidden
        this.add(this.page3);

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
                this.page3.setHidden(true);
                this.page2.setHidden(true);
                this.page1.setHidden(false);
                break;
            case "By Size":
                this.page3.setHidden(true);
                this.page2.setHidden(false);
                this.page1.setHidden(true);
            case "By Tags":
                this.page3.setHidden(false);
                this.page2.setHidden(true);
                this.page1.setHidden(true);
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