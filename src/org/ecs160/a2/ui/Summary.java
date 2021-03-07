package org.ecs160.a2.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Container pageButtonContainer;
    private Map<String, UpdateableContainer> pages;

    /**
     * Default constructor that assembles the children of this container
     */
    public Summary () {
        super(new BoxLayout(BoxLayout.Y_AXIS));
        this.setScrollableY(true); // can scroll vertically

        // title
        this.add(UIUtils.createLabel("Summary", NATIVE_BOLD, COLOR_TITLE,
                                     FONT_SIZE_TITLE));
        
        // setup pages list
        this.pages = new HashMap<>();

        // page button container setup
        pageButtonContainer = new Container();
        this.add(pageButtonContainer);

        // setup pages
        this.addPage("Everything", new SummaryAll());
        this.addPage("By Size", new SummarySize());
        this.addPage("By Tags", new SummaryTags());

        // revalidate the button container
        pageButtonContainer.setLayout(new GridLayout(1, this.pages.size()));
        pageButtonContainer.revalidate();

        // Setup pull to refresh for this container
        this.addPullToRefresh(() -> updateSubContainers());
        this.updateSubContainers();
        this.selectPage("Everything");
    }

    // method to construct page buttons
    private void addPageButton (String text) {
        Button pageButton = new Button (text);
        pageButton.addActionListener((e) -> {
            selectPage(((Button) e.getComponent()).getText());
        });
        pageButtonContainer.add(pageButton);
    }

    // method to add a new page
    private void addPage (String text, UpdateableContainer page) {
        this.addPageButton(text);
        page.setHidden(true);
        this.pages.put(text, page);
        this.add(page);
    }

    // select a page for the given button text
    private void selectPage (String text) {
        this.pages.forEach((key, value) -> value.setHidden(true));
        this.pages.get(text).updateContainer(taskList);
        this.pages.get(text).setHidden(false);
    }

    // reload the internal task list by reading in from the static Database
    private void reloadTaskList () {
        taskList = (List) Database.readAll(Task.OBJECT_ID);
    }

    // for a given page, check if not hidden and update
    private void checkForUpdate (UpdateableContainer page) {
        if (!page.isHidden()) page.updateContainer(taskList);
    }

    /**
     * Update the visible subpages of this Container
     */
    public void updateSubContainers () {
        this.reloadTaskList(); // refresh the tasks first
        if (taskList.size() > 0) {
            this.pages.forEach((key, value) -> checkForUpdate(value));
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